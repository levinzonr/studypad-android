package cz.levinzonr.studypad.domain.models

sealed class InteractorResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : InteractorResult<T>()
    object Loading: InteractorResult<Nothing>()
    data class Error(val exception: Exception) : InteractorResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            is Loading -> "Loading..."
        }
    }
}
