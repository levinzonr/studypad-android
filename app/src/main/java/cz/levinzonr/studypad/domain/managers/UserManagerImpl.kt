package cz.levinzonr.studypad.domain.managers

import cz.levinzonr.studypad.data.EmailLoginRequest
import cz.levinzonr.studypad.data.FacebookLoginRequest
import cz.levinzonr.studypad.rest.Api
import cz.levinzonr.studypad.storage.TokenRepository

class UserManagerImpl(private val api: Api,
                      private val tokenRepository: TokenRepository) : UserManager {



    override suspend fun login(email: String, password: String) {
        val request = EmailLoginRequest(email, password)
        val response = api.loginUsingEmail(request).await()
        tokenRepository.saveToken(response.token)
    }

    override suspend fun loginViaFacebook(token: String) {
        val request = FacebookLoginRequest(token)
        val response = api.loginViaFacebook(request).await()
        tokenRepository.saveToken(response.token)
    }

    override fun isLoggedIn(): Boolean {
        return tokenRepository.getToken() != null
    }
}