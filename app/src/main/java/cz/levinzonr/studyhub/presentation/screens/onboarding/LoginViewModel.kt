package cz.levinzonr.studyhub.presentation.screens.onboarding

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import cz.levinzonr.studyhub.domain.interactors.FacebookLoginInteractor
import cz.levinzonr.studyhub.domain.interactors.LoginInteractor
import cz.levinzonr.studyhub.domain.managers.UserManager
import timber.log.Timber

class LoginViewModel(
    private val userManager: UserManager,
    private val facebookLoginInteractor: FacebookLoginInteractor,
    private val loginInteractor: LoginInteractor) : ViewModel() {



    val stateLiveDate : LiveData<State>
        get() = mutableStateLiveData

    val PERMISSIONS = listOf("email, public_profile")


    private val mutableStateLiveData = MutableLiveData<State>()

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
            mutableStateLiveData.postValue(State.SUCCESS)
        }
    }

    var email: String = ""
    var password: String = ""

    fun login() {
        loginInteractor.input = LoginInteractor.Input(email, password)
        mutableStateLiveData.postValue(State.LOADING)
        loginInteractor.execute {
            onComplete {
                mutableStateLiveData.postValue(State.SUCCESS)
                Timber.d("Success $it")
            }
            onError {
                mutableStateLiveData.postValue(State.ERROR)
                Timber.d("Fail: $it")
            }
        }
    }

     fun handleFacebookLoginResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookActivityResultManager?.onActivityResult(requestCode, resultCode, data)
    }

    fun loginViaFacebook(fragment: LoginFragment) {
        mutableStateLiveData.postValue(State.LOADING)
        LoginManager.getInstance().apply {
            logOut()
            facebookActivityResultManager = CallbackManager.Factory.create()
            registerCallback(facebookActivityResultManager, facebookLoginResultCallback)
            logInWithReadPermissions(fragment, PERMISSIONS)

        }
    }


    private fun onFacebookLoginSuccess(loginResult: LoginResult?) {
        loginResult?.accessToken?.let {
            facebookLoginInteractor.input = FacebookLoginInteractor.Input(it.token)
            facebookLoginInteractor.execute {
                onComplete {
                    mutableStateLiveData.postValue(State.SUCCESS)
                }
            }
        }
    }




    enum class State {
        LOADING, ERROR, SUCCESS
    }



}
