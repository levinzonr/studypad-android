package cz.levinzonr.studypad.domain.managers

interface UserManager {

    suspend fun login(email: String, password: String)

    suspend fun loginViaFacebook(token: String)

    suspend fun createAccount(email: String, password: String, firstName: String, lasName: String)

    fun isLoggedIn() : Boolean

    fun logout()

}