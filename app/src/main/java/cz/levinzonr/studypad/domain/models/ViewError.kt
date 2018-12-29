package cz.levinzonr.studypad.domain.models

sealed class ViewError(val message: String) {

    class ApiError(message: String) : ViewError(message)
    class NetworkError : ViewError("Netwrokd error")
}