package cz.levinzonr.studypad.presentation.screens.onboarding.login

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import cz.levinzonr.studypad.callIf
import cz.levinzonr.studypad.domain.interactors.keychain.FacebookLoginInteractor
import cz.levinzonr.studypad.domain.interactors.keychain.LoginInteractor
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.isValidEmail
import cz.levinzonr.studypad.isValidPassword
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.SimpleEvent
import timber.log.Timber
import com.google.android.gms.common.api.ApiException
import cz.levinzonr.studypad.domain.interactors.keychain.GoogleLoginInteractor
import cz.levinzonr.studypad.presentation.screens.Flow


class LoginViewModel(
    private val userManager: UserManager,
    private val facebookLoginInteractor: FacebookLoginInteractor,
    private val googleLoginInteractor: GoogleLoginInteractor,
    private val loginInteractor: LoginInteractor
) : BaseViewModel() {

    val PERMISSIONS = listOf("email, public_profile")


    val emailValidationEvent = MutableLiveData<SimpleEvent>()
    val passwordValidationEvent = MutableLiveData<SimpleEvent>()

    private var facebookActivityResultManager: CallbackManager? = null
    private val facebookLoginResultCallback = object : FacebookCallback<LoginResult> {
        override fun onSuccess(result: LoginResult?) {
            Timber.d("Facebook Success")
            onFacebookLoginSuccess(result)
        }

        override fun onCancel() {
            Timber.d("Facebook Cancel")
        }

        override fun onError(error: FacebookException?) {
            Timber.d("Facebook Error ${error.toString()}")
        }
    }

    init {
        if (userManager.isLoggedIn()) {
            showLoginSuccess(false)
        }
    }

    var email: String = ""
    var password: String = ""

    fun login() {

        if (allFieldsValid()) {

            loginInteractor.input = LoginInteractor.Input(email, password)
            toggleLoading(true)
            loginInteractor.execute {
                onComplete {
                    toggleLoading(false)
                    showLoginSuccess(it)
                    Timber.d("Success $it")
                }
                onError {
                    postError(it.message)
                    Timber.d("Fail: $it")
                }
            }
        }
    }

    fun handleFacebookLoginResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookActivityResultManager?.onActivityResult(requestCode, resultCode, data)
    }


    fun loginViaFacebook(fragment: LoginFragment) {
        toggleLoading(true)
        LoginManager.getInstance().apply {
            logOut()
            facebookActivityResultManager = CallbackManager.Factory.create()
            registerCallback(facebookActivityResultManager, facebookLoginResultCallback)
            logInWithReadPermissions(fragment, PERMISSIONS)

        }
    }

    fun handleGoogleSigninResult(task: Task<GoogleSignInAccount>) {
        toggleLoading(true)
        try {
            val account = task.getResult(ApiException::class.java)
            Timber.d("Token: ${account?.idToken} ${account?.familyName}")
            account?.idToken?.let(this::onGoogleLoginSuccess)
            // Signed in successfully, show authenticated UI.
        } catch (e: ApiException) {
            Timber.d("Handle google result: $e")
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
        }

    }

    private fun onGoogleLoginSuccess(token: String) {
        googleLoginInteractor.executeWithInput(token) {
            onComplete {
                toggleLoading(false)
                showLoginSuccess(it.newUser)
            }
            onError {
                Timber.d("Error: $it")
            }
        }
    }

    private fun onFacebookLoginSuccess(loginResult: LoginResult?) {
        loginResult?.accessToken?.let {
            facebookLoginInteractor.executeWithInput(it.token) {
                onComplete {
                    toggleLoading(false)
                    showLoginSuccess(it.newUser)
                }
            }
        }
    }

    private fun allFieldsValid(): Boolean {
        val validEmail = email.isValidEmail()
        val passwordEmail = password.isValidPassword()
        emailValidationEvent.callIf(!validEmail)
        passwordValidationEvent.callIf(!passwordEmail)
        return validEmail && passwordEmail
    }

    fun startAccountCreation() {
        navigateTo(LoginFragmentDirections.actionLoginFragment2ToAccountInfoFragment())
    }

    private fun showLoginSuccess(newUser: Boolean) {
        if (newUser) {
            navigateTo(LoginFragmentDirections.actionLoginFragment2ToAccountCreatedFragment())
        } else {
            changeFlow(Flow.MAIN)
        }
    }
}
