package cz.levinzonr.studyhub.domain.managers

interface UserManager {

    suspend fun login(email: String, password: String)

    fun isLoggedIn() : Boolean

}