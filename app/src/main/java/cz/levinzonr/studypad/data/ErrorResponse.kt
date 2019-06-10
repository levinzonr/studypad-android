package cz.levinzonr.studypad.data

import com.google.gson.annotations.SerializedName

/**
 * Data class to represent error response from the API
 */
data class ErrorResponse(
    @SerializedName("message", alternate = ["error_message", "error_description"])
    val message: String
)