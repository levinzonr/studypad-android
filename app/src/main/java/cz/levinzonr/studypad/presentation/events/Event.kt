package cz.levinzonr.studypad.presentation.events

class Event<out T>(private val content: T) {


    private var isAlreadyHandled: Boolean = false

    fun getContentIfNotHandled() : T? {
        return if (isAlreadyHandled) null
        else {
            isAlreadyHandled = true
            content
        }
    }

    fun handle(block: (T) -> Unit) {
        if (!isAlreadyHandled) {
            isAlreadyHandled = true
            block.invoke(content)
        }
    }

}