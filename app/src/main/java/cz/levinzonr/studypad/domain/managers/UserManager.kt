package cz.levinzonr.studypad.domain.managers

interface UserManager {

    suspend fun login(email: String, password: String)

    suspend fun loginViaFacebook(token: String)

    fun isLoggedIn() : Boolean

}