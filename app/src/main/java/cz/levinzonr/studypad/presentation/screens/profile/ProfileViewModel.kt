package cz.levinzonr.studypad.presentation.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.levinzonr.studypad.domain.interactors.GetUserProfileInteractor
import cz.levinzonr.studypad.domain.interactors.keychain.LogoutInteractor
import cz.levinzonr.studypad.domain.interactors.keychain.UpdateUserUniversityInteractor
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.domain.models.UserProfile
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.SimpleEvent
import cz.levinzonr.studypad.presentation.screens.Flow

class ProfileViewModel(
    private val updateUserUniversityInteractor: UpdateUserUniversityInteractor,
    private val getUserProfileInteractor: GetUserProfileInteractor,
    private val logoutInteractor: LogoutInteractor
) : BaseViewModel() {


    init {
        loadProfile()
    }

    val openLoginEvent: LiveData<SimpleEvent>
        get() = userLoggedOutEvent


    private val userLoggedOutEvent = MutableLiveData<SimpleEvent>()
    val profileLiveData = MutableLiveData<UserProfile>()

    fun logout() {

        logoutInteractor.execute {
            onComplete {
                changeFlow(Flow.ONBOARDING)
            }
        }
    }

    fun updateUniversity(university: University) {
        updateUserUniversityInteractor.input = UpdateUserUniversityInteractor.Input(university)
        updateUserUniversityInteractor.execute {
            onComplete { loadProfile() }
        }
    }

    fun onNotificationsButtonClicked() {
        navigateTo(ProfileFragmentDirections.actionProfileFragmentToNotificationsFragment())
    }

    fun loadProfile() {
        getUserProfileInteractor.execute {
            onComplete { profileLiveData.postValue(it) }
        }
    }



}