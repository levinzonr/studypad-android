package cz.levinzonr.studypad.domain.models

import java.lang.Exception

/**
 * Used to serve as an Error Wrapper in Data layer
 */
sealed class ApplicationError {
    /**
     * Api Error is the valid error response received from the API
     * @param message is the error body that was transformed into readable form
     */
    class ApiError(val message: String) : ApplicationError()


    /**
     * Error to describe the problem with the connection
     */
    object NetworkError : ApplicationError()

    /**
     * All the other exception that were not handled properly
     */
    data class GenericError(val exception: Exception): ApplicationError()
}