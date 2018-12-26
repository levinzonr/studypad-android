package cz.levinzonr.studypad.presentation.screens.onboarding.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import cz.levinzonr.studypad.call
import cz.levinzonr.studypad.domain.interactors.GetUniversitiesInteractor
import cz.levinzonr.studypad.domain.interactors.SignupInteractor
import cz.levinzonr.studypad.domain.interactors.UpdateUserUniversityInteractor
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.Event
import cz.levinzonr.studypad.presentation.events.SimpleEvent

class SignupViewModel(
    private val updateUserUniversityInteractor: UpdateUserUniversityInteractor,
    private val signupInteractor: SignupInteractor,
    private val getUniversitiesInteractor: GetUniversitiesInteractor) : BaseViewModel() {

    var email : String = ""
    var password: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var university: University? = null


    val accountCreatedSuccessEvent: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val universitiesLiveData = MutableLiveData<List<University>>()
    val universitySelectedEvent = MutableLiveData<SimpleEvent>()


    init {
        findUnversities("")
    }

    fun findUnversities(query: String) {
        toggleLoading(true)
        getUniversitiesInteractor.unsubscribe()
        getUniversitiesInteractor.input = GetUniversitiesInteractor.Input(query)
        getUniversitiesInteractor.execute {
            onComplete {
                toggleLoading(false)
                universitiesLiveData.postValue(it)
            }
            onError {
                postError("Error loading unis")
            }
        }
    }

    fun createAccount() {
        toggleLoading(true)
        signupInteractor.input = SignupInteractor.Input(firstName, lastName, email, password)
        signupInteractor.execute {
            onComplete {
                toggleLoading(false)
                accountCreatedSuccessEvent.postValue(Event(true))
            }

        }
    }

    fun updateUniversity(university: University) {
        updateUserUniversityInteractor.input = UpdateUserUniversityInteractor.Input(university)
        updateUserUniversityInteractor.execute {
            onComplete {
                universitySelectedEvent.call()
            }
        }
    }
}