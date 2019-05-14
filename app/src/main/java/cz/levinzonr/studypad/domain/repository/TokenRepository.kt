package cz.levinzonr.studypad.domain.repository

interface TokenRepository {

    fun saveToken(token: String, expiresAt: Long)

    fun getToken() : String?

    fun isValid() : Boolean

    fun clear()
}