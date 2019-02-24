package cz.levinzonr.studypad.domain.managers

import com.google.firebase.auth.*
import cz.levinzonr.studypad.data.CreateAccountRequest
import cz.levinzonr.studypad.data.EmailLoginRequest
import cz.levinzonr.studypad.data.FacebookLoginRequest
import cz.levinzonr.studypad.domain.models.UserProfile
import cz.levinzonr.studypad.rest.Api
import cz.levinzonr.studypad.storage.TokenRepository
import cz.levinzonr.studypad.storage.UserProfileRepository
import timber.log.Timber
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
        Timber.d("Save" + response)
        userProfileRepository.saveUserProfile(response)
    }

    override suspend fun loginViaFacebook(token: String) : UserProfile {
        val credentials = FacebookAuthProvider.getCredential(token)
        val authResult =  firebaseAuth.loginWithCredentials(credentials)!!
        val userToken = authResult.user.getCurrentToken()!!
        val response = api.login(userToken.token!!).await()
        tokenRepository.saveToken(userToken.token!!, userToken.expirationTimestamp)
        userProfileRepository.saveUserProfile(response)
        return response
    }

    override suspend fun loginViaGoogle(token: String): UserProfile {
        val credential = GoogleAuthProvider.getCredential(token, null)
        val authResult =  firebaseAuth.loginWithCredentials(credential)!!
        val userToken = authResult.user.getCurrentToken()!!
        val response = api.login(userToken.token!!).await()
        tokenRepository.saveToken(userToken.token!!, userToken.expirationTimestamp)
        Timber.d("Login via google, ${userToken.expirationTimestamp}, ${userToken.token}")
        userProfileRepository.saveUserProfile(response)
        return response
    }

    override suspend fun createAccount(email: String, password: String, firstName: String, lasName: String) : UserProfile {
        val request = CreateAccountRequest(firstName, lasName, email, password)
        val response =  api.createAccount(request).await()
        val result = firebaseAuth.loginWihtCustomToken(response.token)!!
        val userToken = result.user.getCurrentToken()!!
        tokenRepository.saveToken(userToken.token!!, userToken.expirationTimestamp)
        userProfileRepository.saveUserProfile(response.user)
        return response.user
    }

    override fun isLoggedIn(): Boolean {
        return tokenRepository.getToken() != null
    }

    override fun getUserInfo(): UserProfile? {
        return userProfileRepository.getUserProfile()
    }

    override fun logout() {
        firebaseAuth.signOut()
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

    private suspend fun FirebaseAuth.loginWihtCustomToken(token: String) : AuthResult? {
        return suspendCoroutine { cont ->
            signInWithCustomToken(token)
                .addOnCompleteListener {
                    if (it.isComplete && it.isSuccessful) {
                        cont.resume(it.result)
                    } else {
                        cont.resume(null)
                    }
                }
        }
    }

    private suspend fun FirebaseAuth.loginWithCredentials(credential: AuthCredential) : AuthResult? {
        return suspendCoroutine { cont ->
            signInWithCredential(credential)
                .addOnCompleteListener {
                    if (it.isComplete && it.isSuccessful) {
                        cont.resume(it.result)
                    } else {
                        cont.resume(null)
                    }
                }
        }
    }

    private suspend fun FirebaseUser.getCurrentToken() : GetTokenResult? {
        return suspendCoroutine {cont ->
            getIdToken(true)
                .addOnSuccessListener { cont.resume(it) }
                .addOnFailureListener {
                    Timber.d("Fail: $it")
                    cont.resume(null) }

        }
    }
}