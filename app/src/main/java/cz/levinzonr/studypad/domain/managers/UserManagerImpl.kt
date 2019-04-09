package cz.levinzonr.studypad.domain.managers

import androidx.lifecycle.LiveData
import com.google.firebase.auth.*
import cz.levinzonr.studypad.data.CreateAccountRequest
import cz.levinzonr.studypad.domain.models.CurrentUserInfo
import cz.levinzonr.studypad.domain.models.SearchEntry
import cz.levinzonr.studypad.domain.models.UserProfile
import cz.levinzonr.studypad.domain.repository.LocaleRepository
import cz.levinzonr.studypad.domain.repository.SearchHistoryRepository
import cz.levinzonr.studypad.rest.Api
import cz.levinzonr.studypad.storage.TokenRepository
import cz.levinzonr.studypad.storage.UserProfileRepository
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserManagerImpl(private val api: Api,
                      private val localeRepository: LocaleRepository,
                      private val tokenRepository: TokenRepository,
                      private val searchHistoryRepository: SearchHistoryRepository,
                      private val userProfileRepository: UserProfileRepository) : UserManager {


    private val firebaseAuth = FirebaseAuth.getInstance()

    override suspend fun login(email: String, password: String) {
        val result = firebaseAuth.loginWithPassword(email, password)
        val userToken = result.user.getCurrentToken() ?: return
        Timber.d("user tokent $userToken")
        val response = api.loginAsync(userToken.token!!).await()
        tokenRepository.saveToken(userToken.token!!, userToken.expirationTimestamp)
        Timber.d("Save" + response)
        userProfileRepository.saveUserProfile(response)
    }

    override suspend fun loginViaFacebook(token: String) : UserProfile {
        val credentials = FacebookAuthProvider.getCredential(token)
        val authResult =  firebaseAuth.loginWithCredentials(credentials)!!
        val userToken = authResult.user.getCurrentToken()!!
        val response = api.loginAsync(userToken.token!!).await()
        tokenRepository.saveToken(userToken.token!!, userToken.expirationTimestamp)
        userProfileRepository.saveUserProfile(response)
        return response
    }

    override suspend fun loginViaGoogle(token: String): UserProfile {
        val credential = GoogleAuthProvider.getCredential(token, null)
        val authResult =  firebaseAuth.loginWithCredentials(credential)!!
        val userToken = authResult.user.getCurrentToken()!!
        val response = api.loginAsync(userToken.token!!).await()
        tokenRepository.saveToken(userToken.token!!, userToken.expirationTimestamp)
        Timber.d("Login via google, ${userToken.expirationTimestamp}, ${userToken.token}")
        userProfileRepository.saveUserProfile(response)
        return response
    }

    override suspend fun createAccount(email: String, password: String, firstName: String, lasName: String) : UserProfile {
        val request = CreateAccountRequest(firstName, lasName, email, password)
        val response =  api.createAccountAsync(request).await()
        val result = firebaseAuth.loginWihtCustomToken(response.token)!!
        val userToken = result.user.getCurrentToken()!!
        tokenRepository.saveToken(userToken.token!!, userToken.expirationTimestamp)
        userProfileRepository.saveUserProfile(response.user)
        return response.user
    }

    override fun isLoggedIn(): Boolean {
        return tokenRepository.getToken() != null
    }


    override fun getCurrentUserInfo(): CurrentUserInfo? {
        val userInfo = userProfileRepository.getUserProfile() ?: return null
        val locale = localeRepository.getCurrentLocale()
        return CurrentUserInfo(userInfo.uuid, userInfo.displayName, userInfo.university, locale)
    }


    override fun logout() {
        firebaseAuth.signOut()
        tokenRepository.clear()
        userProfileRepository.clear()
        searchHistoryRepository.clear()
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
                    Timber.d("${it.isSuccessful} ${it.isComplete} ${it.exception} ")
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