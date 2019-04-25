package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.domain.models.AuthenticationResult
import cz.levinzonr.studypad.domain.models.UserProfile

interface KeychainRepository {

    suspend fun login(email: String, password: String)

    suspend fun loginViaFacebook(token: String) : UserProfile

    suspend fun loginViaGoogle(token: String) : UserProfile

    suspend fun createAccount(email: String, password: String, firstName: String, lasName: String) : UserProfile
}