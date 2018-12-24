package cz.levinzonr.studypad.presentation.events

class Event<out T>(private val content: T) {


    var isAlreadyHandled: Boolean = false

    fun getContentIfNotHandled() : T? {
        return if (isAlreadyHandled) null
        else {
            isAlreadyHandled = true
            content
        }
    }

}