package cz.levinzonr.studyhub.storage

interface TokenRepository {

    fun saveToken(token: String)

    fun getToken() : String?
}