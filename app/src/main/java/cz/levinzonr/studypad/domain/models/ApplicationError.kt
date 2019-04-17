package cz.levinzonr.studypad.domain.models

sealed class ApplicationError {
    class ApiError(val message: String) : ApplicationError()
    object NetworkError : ApplicationError()
    object GenericError: ApplicationError()
}