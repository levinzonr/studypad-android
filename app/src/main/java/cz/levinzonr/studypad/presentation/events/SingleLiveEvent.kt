package cz.levinzonr.studypad.presentation.events

/**
 * Just like normal event, but without data inside
 * @see Event
 */
class SingleLiveEvent {

    private var isAlradyHandled: Boolean = false


    fun handle(block: () -> Unit) {
        if (!isAlradyHandled) {
            isAlradyHandled = true
            block.invoke()
        }
    }

}