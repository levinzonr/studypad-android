package cz.levinzonr.studypad.data

data class CreateAccountRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)