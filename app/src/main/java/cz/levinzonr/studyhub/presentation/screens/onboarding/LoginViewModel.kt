package cz.levinzonr.studyhub.presentation.screens.onboarding

import androidx.lifecycle.ViewModel
import cz.levinzonr.studyhub.domain.interactors.LoginInteractor
import timber.log.Timber

class LoginViewModel(private val loginInteractor: LoginInteractor) : ViewModel() {

    var email: String = ""
    var password: String = ""

    fun login() {
        loginInteractor.input = LoginInteractor.Input(email, password)
        loginInteractor.execute {
            onComplete {
                Timber.d("Success $it")
            }
            onError {
                Timber.d("Fail: $it")
            }
        }
    }

}
