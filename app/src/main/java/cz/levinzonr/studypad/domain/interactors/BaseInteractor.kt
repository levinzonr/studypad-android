package cz.levinzonr.studypad.domain.interactors



import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.gson.Gson
import cz.levinzonr.studypad.data.ErrorResponse
import cz.levinzonr.studypad.domain.models.ApplicationError
import cz.levinzonr.studypad.fromJson
import kotlinx.coroutines.*
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import kotlin.coroutines.CoroutineContext


/**
 * Abstract class for Interactor
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 * <p>
 * By convention each Interactor implementation will return the result using a coroutine
 * that will execute its job in a runAsync thread and will post the result in the UI thread.
 */
typealias CompletionBlock<T> = BaseInteractor.Request<T>.() -> Unit

abstract class BaseInteractor<T> {

    private var parentJob: Job = Job()
    var backgroundContext: CoroutineContext = Dispatchers.IO
    var foregroundContext: CoroutineContext = Dispatchers.Main

    protected abstract suspend fun executeOnBackground(): T

    fun execute(block: CompletionBlock<T>) {
        val response = Request<T>().apply { block() }
        unsubscribe()
        parentJob = Job()
        CoroutineScope(foregroundContext + parentJob).launch {
            try {
                val result = withContext(backgroundContext) {
                    executeOnBackground()
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
                val errorBody = e.response().errorBody()?.string() ?: ""
                val errorResponse = Gson().fromJson<cz.levinzonr.studypad.domain.models.ErrorResponse>(errorBody)
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


    class Request<T> {
        private var onComplete: ((T) -> Unit)? = null
        private var onError: ((ApplicationError) -> Unit)? = null
        private var onCancel: ((CancellationException) -> Unit)? = null

        fun onComplete(block: (T) -> Unit) {
            onComplete = block
        }

        fun onError(block: (ApplicationError) -> Unit) {
            onError = block
        }

        fun onCancel(block: (CancellationException) -> Unit) {
            onCancel = block
        }

        operator fun invoke(result: T) {
            onComplete?.let {
                it.invoke(result)
            }
        }

        operator fun invoke(error: ApplicationError) {
            onError?.let {
                it.invoke(error)
            }
        }

        operator fun invoke(error: CancellationException) {
            onCancel?.let {
                it.invoke(error)
            }
        }
    }
}