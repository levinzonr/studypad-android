package cz.levinzonr.studypad.presentation.events

/**
 * Generic event that is used in LiveData to handle
 * specific event only once
 * @param T specifies the type of the data to handle
 * @param content actual data of type <T>
 */
class Event<out T>(private val content: T) {

    /**
     * Flag that determines whether the event was handled or not
     */
    private var isAlreadyHandled: Boolean = false

    /**
     * Retrieve the data if event was not yet handled
     * @return data of type T if event was not handled
     * @return null if the event was already  handled
     */
    fun getContentIfNotHandled() : T? {
        return if (isAlreadyHandled) null
        else {
            isAlreadyHandled = true
            content
        }
    }

    /**
     * Handle the event using using lambda function
     * @param block - anonymous function to invoke if the event was not handled
     */
    fun handle(block: (T) -> Unit) {
        if (!isAlreadyHandled) {
            isAlreadyHandled = true
            block.invoke(content)
        }
    }

}