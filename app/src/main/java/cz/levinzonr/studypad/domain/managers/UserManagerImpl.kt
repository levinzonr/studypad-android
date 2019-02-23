package cz.levinzonr.studypad.domain.managers

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GetTokenResult
import cz.levinzonr.studypad.data.CreateAccountRequest
import cz.levinzonr.studypad.data.EmailLoginRequest
import cz.levinzonr.studypad.data.FacebookLoginRequest
import cz.levinzonr.studypad.domain.models.UserProfile
import cz.levinzonr.studypad.rest.Api
import cz.levinzonr.studypad.storage.TokenRepository
import cz.levinzonr.studypad.storage.UserProfileRepository
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserManagerImpl(private val api: Api,
                      private val tokenRepository: TokenRepository,
                      private val userProfileRepository: UserProfileRepository) : UserManager {


    private val firebaseAuth = FirebaseAuth.getInstance()

    override suspend fun login(email: String, password: String) {
        val result = firebaseAuth.loginWithPassword(email, password)
        val userToken = result.user.getCurrentToken() ?: return
        val response = api.login(userToken.token!!).await()
        tokenRepository.saveToken(userToken.token!!, userToken.expirationTimestamp)
        userProfileRepository.saveUserProfile(response)
    }

    override suspend fun loginViaFacebook(token: String) : UserProfile {
        val request = FacebookLoginRequest(token)
        val response = api.loginViaFacebook(request).await()
        // romatokenRepository.saveToken(response.token)
        userProfileRepository.saveUserProfile(response.user)
        return response.user
    }

    override suspend fun createAccount(email: String, password: String, firstName: String, lasName: String) : String {
        val request = CreateAccountRequest(firstName, lasName, email, password)
        return api.createAccount(request).await().token
    }

    override fun isLoggedIn(): Boolean {
        return tokenRepository.getToken() != null
    }

    override fun getUserInfo(): UserProfile? {
        return userProfileRepository.getUserProfile()
    }

    override fun logout() {
        tokenRepository.clear()
        userProfileRepository.clear()
    }

    private suspend fun FirebaseAuth.loginWithPassword(email: String, password: String) : AuthResult {
        return suspendCoroutine { cont ->
            signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { cont.resume(it) }
                .addOnFailureListener { cont.resumeWithException(it) }
        }
    }

    private suspend fun FirebaseUser.getCurrentToken() : GetTokenResult?? {
        return suspendCoroutine {cont ->
            getIdToken(true)
                .addOnSuccessListener { cont.resume(it) }
                .addOnFailureListener { cont.resume(null) }

        }
    }
}