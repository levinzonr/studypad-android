package cz.levinzonr.studypad.domain.managers

import cz.levinzonr.studypad.data.CreateAccountRequest
import cz.levinzonr.studypad.data.EmailLoginRequest
import cz.levinzonr.studypad.data.FacebookLoginRequest
import cz.levinzonr.studypad.domain.models.UserProfile
import cz.levinzonr.studypad.rest.Api
import cz.levinzonr.studypad.storage.TokenRepository

class UserManagerImpl(private val api: Api,
                      private val tokenRepository: TokenRepository) : UserManager {



    override suspend fun login(email: String, password: String) {
        val request = EmailLoginRequest(email, password)
        val response = api.loginUsingEmail(request).await()
        tokenRepository.saveToken(response.token)
    }

    override suspend fun loginViaFacebook(token: String) : UserProfile {
        val request = FacebookLoginRequest(token)
        val response = api.loginViaFacebook(request).await()
        tokenRepository.saveToken(response.token)
        return response.user
    }

    override suspend fun createAccount(email: String, password: String, firstName: String, lasName: String) {
        val request = CreateAccountRequest(firstName, lasName, email, password)
        val response = api.createAccount(request).await()
        tokenRepository.saveToken(response.token)
    }

    override fun isLoggedIn(): Boolean {
        return tokenRepository.getToken() != null
    }

    override fun logout() {
        tokenRepository.clear()
    }
}