package cz.levinzonr.studypad.domain.repository

/**
 * Repository to store firebase access token
 */
interface FirebaseTokenRepository {

    /**
     * Sets a new access token
     * @param token new token value
     */
    fun putToken(token: String)

    /**
     * Get an access toke
     * @return the current access token, null there is no token
     */
    fun getToken() : String?


    fun markAsRefreshed()
    fun isTokenRefreshed() : Boolean
}