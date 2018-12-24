package cz.levinzonr.studypad.storage

interface TokenRepository {

    fun saveToken(token: String)

    fun getToken() : String?

    fun clear()
}