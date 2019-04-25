package cz.levinzonr.studypad.domain.models

import java.lang.Exception

sealed class ApplicationError {
    class ApiError(val message: String) : ApplicationError()
    object NetworkError : ApplicationError()
    data class GenericError(val exception: Exception): ApplicationError()
}