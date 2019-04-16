package cz.levinzonr.studypad.presentation.events

class SingleLiveEvent {

    private var isAlradyHandled: Boolean = false


    fun handle(block: () -> Unit) {
        if (!isAlradyHandled) {
            isAlradyHandled = true
            block.invoke()
        }
    }

}