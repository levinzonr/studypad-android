package cz.levinzonr.studypad.presentation.screens.onboarding.signup

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.*
import cz.levinzonr.studypad.domain.interactors.GetUniversitiesInteractor
import cz.levinzonr.studypad.domain.interactors.keychain.SignupInteractor
import cz.levinzonr.studypad.domain.interactors.keychain.UpdateUserInteractor
import cz.levinzonr.studypad.domain.models.ApplicationError
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.domain.models.ViewError
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.Event
import cz.levinzonr.studypad.presentation.events.SingleLiveEvent
import cz.levinzonr.studypad.presentation.screens.Flow
import timber.log.Timber
import java.lang.Exception

class SignupViewModel(
    private val updateUserInteractor: UpdateUserInteractor,
    private val signupInteractor: SignupInteractor,
    private val getUniversitiesInteractor: GetUniversitiesInteractor
) : BaseViewModel() {

    var email: String = ""
    var password: String = ""
    var firstName: String = ""
    var lastName: String = ""


    var university: University? = null

    val credentiasViewState = MutableLiveData<CredentiasViewState>()
    val accountInfoViewState = MutableLiveData<AccountInfoViewState>()

    init {
        credentiasViewState.postValue(CredentiasViewState())
        accountInfoViewState.postValue(AccountInfoViewState())
    }

    fun onCreateAccountButtonClicked() {
        if (validateCredentials()) {
            toggleLoading(true)
            signupInteractor.executeWithInput(SignupInteractor.Input(firstName, lastName, email, password)) {
                onComplete {
                    toggleLoading(false)
                    Timber.d("Firebase token: $it")
                    navigateTo(CredentialsInfoFragmentDirections.actionCredentialsInfoFragmentToAccountCreatedFragment())
                }
                onError {
                    when (it) {
                        is ApplicationError.NetworkError -> handleApplicationError(it)
                        is ApplicationError.ApiError -> showError(
                            ViewError.DialogError(
                                string(R.string.error_signup_title),
                                it.message
                            )
                        )
                        is ApplicationError.GenericError -> handleExceptionError(it.exception)
                    }
                }

            }
        }
    }

    private fun handleExceptionError(exception: Exception) {
        showError(ViewError.DialogError(string(R.string.error_signup_title), exception.localizedMessage))
    }

    fun updateUniversity(university: University) {
        updateUserInteractor.input = UpdateUserInteractor.Input(university)
        updateUserInteractor.execute {
            onComplete {
                showMain()
            }
            onError {
                handleApplicationError(it)
            }
        }
    }


    fun onSelectUniversityClicked() {
        navigateTo(AccountCreatedFragmentDirections.actionGlobalUniversitySelectorFragment(true))
    }


    fun showMain() {
        changeFlow(Flow.MAIN)
    }

    fun onNextStepButtonClicked() {
        if (validateAccountInfo())
            navigateTo(AccountInfoFragmentDirections.actionAccountInfoFragmentToCredentialsInfoFragment())
    }

    private fun validateCredentials(): Boolean {
        val currentState = credentiasViewState.value ?: CredentiasViewState()
        val pwdEvent: Event<String>? = when {
            password.isBlank() -> Event(string(R.string.error_required_field))
            !password.isValidPassword() -> Event(string(R.string.signup_password_error))
            else -> null
        }

        val emailEvent: Event<String>? = when {
            email.isBlank() -> Event(string(R.string.error_required_field))
            !email.isValidEmail() -> Event(string(R.string.signup_email_error))
            else -> null
        }

        credentiasViewState.postValue(currentState.copy(pwdEvent, emailEvent))
        return emailEvent == null && pwdEvent == null
    }


    private fun validateAccountInfo(): Boolean {
        val currentState = accountInfoViewState.value ?: AccountInfoViewState()
        val firstNameEvent: Event<String>? = when {
            firstName.isBlank() -> Event(string(R.string.error_required_field))
            !firstName.isValidName() -> Event(string(R.string.signup_firstname_error))
            else -> null
        }

        val lastNameEvent: Event<String>? = when {
            lastName.isBlank() -> Event(string(R.string.error_required_field))
            !lastName.isValidName() -> Event(string(R.string.signup_lastname_error))
            else -> null
        }

        accountInfoViewState.postValue(currentState.copy(firstNameEvent, lastNameEvent))
        return firstNameEvent == null && lastNameEvent == null
    }


    fun onBackButtonClicked() {
        navigateBack()
    }

}