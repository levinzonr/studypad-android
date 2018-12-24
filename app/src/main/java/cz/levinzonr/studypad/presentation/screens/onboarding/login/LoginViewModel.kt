package cz.levinzonr.studypad.presentation.screens.onboarding.login

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import cz.levinzonr.studypad.domain.interactors.FacebookLoginInteractor
import cz.levinzonr.studypad.domain.interactors.LoginInteractor
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.SimpleEvent
import timber.log.Timber

class LoginViewModel(
    private val userManager: UserManager,
    private val facebookLoginInteractor: FacebookLoginInteractor,
    private val loginInteractor: LoginInteractor) : BaseViewModel() {

    val PERMISSIONS = listOf("email, public_profile")

    val loginSuccessEvent = MutableLiveData<SimpleEvent>()


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
          loginSuccessEvent.postValue(SimpleEvent())
        }
    }

    var email: String = ""
    var password: String = ""

    fun login() {
        loginInteractor.input = LoginInteractor.Input(email, password)
        toggleLoading(true)
        loginInteractor.execute {
            onComplete {
                toggleLoading(false)
                loginSuccessEvent.postValue(SimpleEvent())
                Timber.d("Success $it")
            }
            onError {
                postError("Error")
                Timber.d("Fail: $it")
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
        val token = task.result?.idToken
        Timber.d(token)
    }


    private fun onFacebookLoginSuccess(loginResult: LoginResult?) {
        loginResult?.accessToken?.let {
            facebookLoginInteractor.input = FacebookLoginInteractor.Input(it.token)
            facebookLoginInteractor.execute {
                onComplete {
                   toggleLoading(false)
                    loginSuccessEvent.postValue(SimpleEvent())
                }
            }
        }
    }
}
