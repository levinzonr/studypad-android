package cz.levinzonr.studypad.domain.models


sealed class ViewError {
    data class DialogError(val title: String, val message: String) : ViewError()
    data class ToastError(val message: String) : ViewError()
}