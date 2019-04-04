package cz.levinzonr.studypad.presentation.screens.onboarding.signup

import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import cz.levinzonr.studypad.*
import cz.levinzonr.studypad.domain.interactors.GetUniversitiesInteractor
import cz.levinzonr.studypad.domain.interactors.keychain.SignupInteractor
import cz.levinzonr.studypad.domain.interactors.keychain.UpdateUserUniversityInteractor
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.Event
import cz.levinzonr.studypad.presentation.events.SimpleEvent
import cz.levinzonr.studypad.presentation.screens.Flow
import cz.levinzonr.studypad.presentation.screens.onboarding.login.LoginFragmentDirections
import timber.log.Timber

class SignupViewModel(
    private val updateUserUniversityInteractor: UpdateUserUniversityInteractor,
    private val signupInteractor: SignupInteractor,
    private val getUniversitiesInteractor: GetUniversitiesInteractor) : BaseViewModel() {

    var email : String = ""
    var password: String = ""


    var firstName: String = ""
        set(value) {
            field = value
            validateAccountInfo()
        }

    var lastName: String = ""
        set(value) {
            field = value
            validateAccountInfo()
        }

    var university: University? = null


    val accountCreatedSuccessEvent: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val universitiesLiveData = MutableLiveData<List<University>>()
    val universitySelectedEvent = MutableLiveData<SimpleEvent>()

    val validAccountInfoEvent = MutableLiveData<Boolean>()

    val invalidPasswordEvent = liveEvent()
    val invalidEmmailEvent = liveEvent()




    fun createAccount() {
        if (validateCredentials()) {
            toggleLoading(true)
            signupInteractor.input = SignupInteractor.Input(firstName, lastName, email, password)
            signupInteractor.execute {
                onComplete {
                    toggleLoading(false)
                    Timber.d("Firebase token: $it")
                    accountCreatedSuccessEvent.call(it.newUser)
                }
                onError {
                    postError(it.message)
                }

            }
        }
    }

    fun updateUniversity(university: University) {
        updateUserUniversityInteractor.input = UpdateUserUniversityInteractor.Input(university)
        updateUserUniversityInteractor.execute {
            onComplete {
                showMain()
            }
            onError {
                postError(it.message)
            }
        }
    }


    private fun validateCredentials() : Boolean {
        invalidEmmailEvent.callIf(!email.isValidEmail())
        invalidPasswordEvent.callIf(!password.isValidPassword())
        return password.isValidPassword() && email.isValidEmail()
    }

    private fun validateAccountInfo() {
        validAccountInfoEvent.postValue(firstName.isNotEmpty() && lastName.isNotEmpty())
    }


    fun showMain() {
        changeFlow(Flow.MAIN)
    }

    fun showAccountInfo() {
        navigateTo(AccountInfoFragmentDirections.actionAccountInfoFragmentToCredentialsInfoFragment())
    }


}