package cz.levinzonr.studyhub.domain.managers

import cz.levinzonr.studyhub.data.EmailLoginRequest
import cz.levinzonr.studyhub.rest.utils.Api
import cz.levinzonr.studyhub.storage.TokenRepository

class UserManagerImpl(private val api: Api,
                      private val tokenRepository: TokenRepository) : UserManager {



    override suspend fun login(email: String, password: String) {
        val request = EmailLoginRequest(email, password)
        val response = api.loginUsingEmail(request).await()
        tokenRepository.saveToken(response.token)
    }

    override fun isLoggedIn(): Boolean {
        return tokenRepository.getToken() != null
    }
}