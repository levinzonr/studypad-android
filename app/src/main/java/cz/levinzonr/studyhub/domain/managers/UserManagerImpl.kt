package cz.levinzonr.studyhub.domain.managers

import cz.levinzonr.studyhub.data.EmailLoginRequest
import cz.levinzonr.studyhub.data.FacebookLoginRequest
import cz.levinzonr.studyhub.rest.Api
import cz.levinzonr.studyhub.storage.TokenRepository

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