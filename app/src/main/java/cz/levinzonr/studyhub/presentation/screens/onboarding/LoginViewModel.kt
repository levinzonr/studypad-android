package cz.levinzonr.studyhub.presentation.screens.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.levinzonr.studyhub.domain.interactors.LoginInteractor
import cz.levinzonr.studyhub.domain.managers.UserManager
import timber.log.Timber

class LoginViewModel(
    private val userManager: UserManager,
    private val loginInteractor: LoginInteractor) : ViewModel() {



    val stateLiveDate : LiveData<State>
        get() = mutableStateLiveData

    private val mutableStateLiveData = MutableLiveData<State>()

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




    enum class State {
        LOADING, ERROR, SUCCESS
    }



}
