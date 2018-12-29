package cz.levinzonr.studypad.domain.interactors



import com.google.gson.Gson
import cz.levinzonr.studypad.data.ErrorResponse
import cz.levinzonr.studypad.domain.models.ViewError
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

    private fun handleException(e: Exception) : ViewError {
        return when(e) {
            is HttpException -> {
                val errorBody = e.response().errorBody()?.string() ?: "{}"
                val gson = Gson().fromJson<ErrorResponse>(errorBody, ErrorResponse::class.java)
                return ViewError.ApiError(gson.message)
            }
            is IOException -> ViewError.NetworkError()
            else -> ViewError.ApiError("Else: $e")
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
        private var onError: ((ViewError) -> Unit)? = null
        private var onCancel: ((CancellationException) -> Unit)? = null

        fun onComplete(block: (T) -> Unit) {
            onComplete = block
        }

        fun onError(block: (ViewError) -> Unit) {
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

        operator fun invoke(error: ViewError) {
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