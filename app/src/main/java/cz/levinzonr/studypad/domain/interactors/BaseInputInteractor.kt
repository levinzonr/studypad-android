package cz.levinzonr.studypad.domain.interactors

import com.google.firebase.FirebaseNetworkException
import cz.levinzonr.studypad.domain.models.ApplicationError
import kotlinx.coroutines.*
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import kotlin.coroutines.CoroutineContext


abstract class BaseInputInteractor<in I, O> {


    private var parentJob: Job = Job()
    var backgroundContext: CoroutineContext = Dispatchers.IO
    var foregroundContext: CoroutineContext = Dispatchers.Main

    protected abstract suspend fun executeOnBackground(input: I): O

    fun executeWithInput(input: I, block: CompletionBlock<O>) {
        val response = BaseInteractor.Request<O>().apply { block() }
        unsubscribe()
        parentJob = Job()
        CoroutineScope(foregroundContext + parentJob).launch {
            try {
                val result = withContext(backgroundContext) {
                    executeOnBackground(input)
                }
                response(result)
            } catch (cancellationException: CancellationException) {
                Timber.d("canceled by user")
                response(cancellationException)
            } catch (e: Exception) {
                Timber.e(e)
                response(handleException(e))
            }
        }
    }

    private fun handleException(e: Exception) : ApplicationError {
        return when(e) {
            is HttpException -> {
                Timber.d("HttpException")
                val errorBody = e.response().body().toString() ?: "empty"
                return ApplicationError.ApiError(errorBody)
            }
            is IOException, is FirebaseNetworkException -> ApplicationError.NetworkError
            else -> ApplicationError.ApiError("Else: $e")
        }
    }

    protected suspend fun <X> runAsync(context: CoroutineContext = backgroundContext, block: suspend () -> X): Deferred<X> {
        return CoroutineScope(context + parentJob).async {
            block.invoke()
        }
    }

    fun unsubscribe() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }


}