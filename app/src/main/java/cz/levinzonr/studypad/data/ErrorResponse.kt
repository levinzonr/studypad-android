package cz.levinzonr.studypad.data

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message", alternate = ["error_message"])
    val message: String
)