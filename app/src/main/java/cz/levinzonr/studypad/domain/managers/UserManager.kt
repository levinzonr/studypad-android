package cz.levinzonr.studypad.domain.managers

import cz.levinzonr.studypad.domain.models.UserProfile

interface UserManager {

    suspend fun login(email: String, password: String)

    suspend fun loginViaFacebook(token: String) : UserProfile

    suspend fun loginViaGoogle(token: String) : UserProfile

    suspend fun createAccount(email: String, password: String, firstName: String, lasName: String) : UserProfile

    fun isLoggedIn() : Boolean

    fun logout()

    fun getUserInfo() : UserProfile?

}