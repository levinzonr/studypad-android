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
import cz.levinzonr.studypad.presentation.events.SingleLiveEvent
import timber.log.Timber
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.interactors.keychain.GoogleLoginInteractor
import cz.levinzonr.studypad.domain.models.ApplicationError
import cz.levinzonr.studypad.domain.models.ViewError
import cz.levinzonr.studypad.presentation.events.Event
import cz.levinzonr.studypad.presentation.screens.Flow
import java.lang.Exception


class LoginViewModel(
    private val userManager: UserManager,
    private val facebookLoginInteractor: FacebookLoginInteractor,
    private val googleLoginInteractor: GoogleLoginInteractor,
    private val loginInteractor: LoginInteractor
) : BaseViewModel() {

    val PERMISSIONS = listOf("email, public_profile")

    val validationViewState: MutableLiveData<LoginViewState> = MutableLiveData()

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
            showError(ViewError.DialogError(string(R.string.error_login_title), error?.localizedMessage ?: string(R.string.error_unknown_message)))
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
            toggleLoading(true)
            loginInteractor.executeWithInput(LoginInteractor.Input(email, password)) {
                onComplete {
                    toggleLoading(false)
                    showLoginSuccess(false)
                    Timber.d("Success $it")
                }
                onError {
                    when(it) {
                        is ApplicationError.NetworkError -> handleApplicationError(it)
                        is ApplicationError.ApiError -> showError(ViewError.DialogError(string(R.string.error_login_title),  it.message))
                        is ApplicationError.GenericError -> handleExceptionError(it.exception)
                    }
                }
            }
        }
    }

    fun handleFacebookLoginResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookActivityResultManager?.onActivityResult(requestCode, resultCode, data)
    }


    fun loginViaFacebook(fragment: LoginFragment) {
        LoginManager.getInstance().apply {
            logOut()
            facebookActivityResultManager = CallbackManager.Factory.create()
            registerCallback(facebookActivityResultManager, facebookLoginResultCallback)
            logInWithReadPermissions(fragment, PERMISSIONS)

        }
    }

    fun handleGoogleSigninResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            Timber.d("Token: ${account?.idToken} ${account?.familyName}")
            account?.idToken?.let(this::onGoogleLoginSuccess)
            // Signed in successfully, show authenticated UI.
        } catch (e: ApiException) {
            Timber.d("Handle google result: $e")
            handleExceptionError(e)
        }

    }

    private fun onGoogleLoginSuccess(token: String) {
        toggleLoading(true)
        googleLoginInteractor.executeWithInput(token) {
            onComplete {
                toggleLoading(false)
                showLoginSuccess(it.newUser)
            }
            onError {
                Timber.d("Error: $it")
                when(it) {
                    is ApplicationError.NetworkError -> handleApplicationError(it)
                    is ApplicationError.ApiError -> showError(ViewError.DialogError(string(R.string.error_login_title), it.message))
                    is ApplicationError.GenericError -> handleExceptionError(it.exception)
                }
            }
        }
    }

    private fun onFacebookLoginSuccess(loginResult: LoginResult?) {
        toggleLoading(true)
        loginResult?.accessToken?.let {
            facebookLoginInteractor.executeWithInput(it.token) {
                onComplete {
                    toggleLoading(false)
                    showLoginSuccess(it.newUser)
                }
                onError {
                    toggleLoading(false)
                    when(it) {
                        is ApplicationError.NetworkError -> handleApplicationError(it)
                        is ApplicationError.ApiError -> showError(ViewError.DialogError(string(R.string.error_login_title), it.message))
                        is ApplicationError.GenericError -> handleExceptionError(it.exception)
                    }
                }
            }
        }
    }

    private fun handleExceptionError(exception: Exception) {
        Timber.d("Exceptiopn: $exception")
        // Credentials Error
        if (exception is FirebaseAuthInvalidCredentialsException || exception is FirebaseAuthInvalidUserException) {
            showError(ViewError.DialogError(string(R.string.error_login_title), string(R.string.error_login_credentials_message)))
        } else {
            handleApplicationError(ApplicationError.GenericError(exception))
        }
    }

    private fun allFieldsValid(): Boolean {
        val validEmail = when {
            email.isBlank() -> Event(string(R.string.error_required_field))
            !email.isValidEmail() -> Event(string(R.string.signup_email_error))
            else -> null
        }
        val passwordEmail = when {
            password.isBlank() -> Event(string(R.string.error_required_field))
            else -> null
        }
        val currentState = validationViewState.value ?: LoginViewState()
        validationViewState.postValue(currentState.copy(validEmail, passwordEmail))
        return validEmail == null && passwordEmail == null
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
