package cz.levinzonr.studypad.presentation.screens.onboarding.signup

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.*
import cz.levinzonr.studypad.domain.interactors.GetUniversitiesInteractor
import cz.levinzonr.studypad.domain.interactors.keychain.SignupInteractor
import cz.levinzonr.studypad.domain.interactors.keychain.UpdateUserInteractor
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.Event
import cz.levinzonr.studypad.presentation.events.SingleLiveEvent
import cz.levinzonr.studypad.presentation.screens.Flow
import timber.log.Timber

class SignupViewModel(
    private val updateUserInteractor: UpdateUserInteractor,
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



    val validAccountInfoEvent = MutableLiveData<Boolean>()

    val invalidPasswordEvent = liveEvent()
    val invalidEmmailEvent = liveEvent()




    fun createAccount() {
        if (validateCredentials()) {
            toggleLoading(true)
            signupInteractor.executeWithInput( SignupInteractor.Input(firstName, lastName, email, password)) {
                onComplete {
                    toggleLoading(false)
                    Timber.d("Firebase token: $it")
                    navigateTo(CredentialsInfoFragmentDirections.actionCredentialsInfoFragmentToAccountCreatedFragment())
                }
                onError {


                }

            }
        }
    }

    fun updateUniversity(university: University) {
        updateUserInteractor.input = UpdateUserInteractor.Input(university)
        updateUserInteractor.execute {
            onComplete {
                showMain()
            }
            onError {

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