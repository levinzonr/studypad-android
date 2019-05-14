package cz.levinzonr.studypad.domain.interactors

import com.google.firebase.FirebaseNetworkException
import com.google.gson.Gson
import cz.levinzonr.studypad.domain.models.ApplicationError
import cz.levinzonr.studypad.domain.models.ErrorResponse
import cz.levinzonr.studypad.fromJson
import kotlinx.coroutines.*
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import kotlin.coroutines.CoroutineContext

/**
 * Similar to BaseInteractor, but provides a  better way to handle the input
 * @param <I> specifies the input type
 * @param <O> specifies the output type
 */
abstract class BaseInputInteractor<in I, O> {


    /**
     * Parent job reference for to be able to cancel the interactor
     */
    private var parentJob: Job = Job()

    var backgroundContext: CoroutineContext = Dispatchers.IO
    var foregroundContext: CoroutineContext = Dispatchers.Main

    protected abstract suspend fun executeOnBackground(input: I): O


    /**
     * Executes interactor with specified Input and provides the result via callback
     * @param input I is the interactor Input
     * @param block is the callback that will be used to provide an operation result
     */
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

    /**
     * Transforms an Exception into the object that will be handled by ViewModel
     * @param e is the exception to handle
     * @return ApplicationError that serves as an exception wrapper that is easier to handle
     * @see ApplicationError
     */
    private fun handleException(e: Exception) : ApplicationError {
        return when(e) {
            is HttpException -> {
                val errorBody = e.response().errorBody()?.string() ?: ""
                val errorResponse = Gson().fromJson<ErrorResponse>(errorBody)
                return if (e.code() < 500 && errorResponse != null)
                    ApplicationError.ApiError(errorResponse.message)
                else ApplicationError.GenericError(e)
            }
            is IOException, is FirebaseNetworkException -> ApplicationError.NetworkError
            else -> ApplicationError.GenericError(e)
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