package cz.levinzonr.studypad.domain.models

data class ErrorResponse(
    val type: String,
    val message: String
)