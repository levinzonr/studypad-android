package cz.levinzonr.studypad.data

/**
 * Defines the payload/body for the create user request
 * @param firstName - first name of the user
 * @param lastName - user's last name
 * @param email - user email
 * @param password - user's password
 */
data class CreateAccountRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)