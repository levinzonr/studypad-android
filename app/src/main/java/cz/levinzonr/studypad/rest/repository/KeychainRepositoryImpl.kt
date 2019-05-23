package cz.levinzonr.studypad.rest.repository

import com.google.firebase.auth.*
import cz.levinzonr.studypad.data.CreateAccountRequest
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.UserProfile
import cz.levinzonr.studypad.domain.repository.KeychainRepository
import cz.levinzonr.studypad.rest.Api
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Keychain repository Implementation
 * @param api - retrofit api instance
 * @see KeychainRepository
 */
class KeychainRepositoryImpl(private val api: Api,
                             private val userManager: UserManager) :
    KeychainRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override suspend fun login(email: String, password: String) {
        val result = firebaseAuth.loginWithPassword(email, password)
        val userToken = result.user.getCurrentToken()
        Timber.d("user tokent $userToken")
        val response = api.loginAsync(userToken.token!!).await()
        userManager.afterSuccessfulLogin(response, userToken.token!!)
    }



    override suspend fun loginViaFacebook(token: String) : UserProfile {
        val credentials = FacebookAuthProvider.getCredential(token)
        val authResult =  firebaseAuth.loginWithCredentials(credentials)
        val userToken = authResult.user.getCurrentToken()
        val response = api.loginAsync(userToken.token!!).await()
        userManager.afterSuccessfulLogin(response, userToken.token!!)
        return response
    }

    override suspend fun loginViaGoogle(token: String): UserProfile {
        val credential = GoogleAuthProvider.getCredential(token, null)
        val authResult =  firebaseAuth.loginWithCredentials(credential)
        val userToken = authResult.user.getCurrentToken()
        val response = api.loginAsync(userToken.token!!).await()
        userManager.afterSuccessfulLogin(response, userToken.token!!)
        return response
    }

    override suspend fun createAccount(email: String, password: String, firstName: String, lasName: String) : UserProfile {
        val request = CreateAccountRequest(firstName, lasName, email, password)
        val response =  api.createAccountAsync(request).await()
        val result = firebaseAuth.loginWihtCustomToken(response.token)

        val userToken = result.user.getCurrentToken()
        userManager.afterSuccessfulLogin(response.user, userToken.token!!)
        return response.user
    }


    private suspend fun FirebaseAuth.loginWithPassword(email: String, password: String) : AuthResult {
        return suspendCoroutine { cont ->
            signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { cont.resume(it) }
                .addOnFailureListener { cont.resumeWithException(it) }
        }
    }

    private suspend fun FirebaseAuth.loginWihtCustomToken(token: String) : AuthResult {
        return suspendCoroutine { cont ->
            signInWithCustomToken(token)
                .addOnSuccessListener { cont.resume(it) }
                .addOnFailureListener { cont.resumeWithException(it) }
        }
    }

    private suspend fun FirebaseAuth.loginWithCredentials(credential: AuthCredential) : AuthResult {
        return suspendCoroutine { cont ->
            signInWithCredential(credential)
                .addOnSuccessListener { cont.resume(it) }
                .addOnFailureListener { cont.resumeWithException(it) }
        }
    }

    private suspend fun FirebaseUser.getCurrentToken() : GetTokenResult {
        return suspendCoroutine {cont ->
            getIdToken(true)
                .addOnSuccessListener { cont.resume(it) }
                .addOnFailureListener { cont.resumeWithException(it) }
        }
    }

}