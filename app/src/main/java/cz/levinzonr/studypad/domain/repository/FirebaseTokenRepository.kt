package cz.levinzonr.studypad.domain.repository

interface FirebaseTokenRepository {

    fun putToken(token: String)
    fun markAsRefreshed()
    fun isTokenRefreshed() : Boolean
    fun getToken() : String?
}