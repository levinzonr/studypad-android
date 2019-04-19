package cz.levinzonr.studypad.presentation.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.GetUserProfileInteractor
import cz.levinzonr.studypad.domain.interactors.keychain.LogoutInteractor
import cz.levinzonr.studypad.domain.interactors.keychain.UpdateUserInteractor
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.domain.models.UserProfile
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.SingleLiveEvent
import cz.levinzonr.studypad.presentation.screens.Flow

class ProfileViewModel(
    private val updateUserInteractor: UpdateUserInteractor,
    private val getUserProfileInteractor: GetUserProfileInteractor,
    private val logoutInteractor: LogoutInteractor
) : BaseViewModel() {


    init {
        loadProfile()
    }


    val profileLiveData = MutableLiveData<UserProfile>()

    fun logout() {

        logoutInteractor.execute {
            onComplete {
                changeFlow(Flow.ONBOARDING)
            }
        }
    }

    fun updateUniversity(university: University) {
        updateUserInteractor.input = UpdateUserInteractor.Input(university)
        updateUserInteractor.execute {
            onComplete { loadProfile() }
        }
    }

    fun onNotificationsButtonClicked() {
        navigateTo(ProfileFragmentDirections.actionProfileFragmentToNotificationsFragment())
    }

    fun onEditProfileButtonClicked() {
        navigateTo(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
    }

    fun loadProfile() {
        getUserProfileInteractor.execute {
            onComplete { profileLiveData.postValue(it) }
        }
    }

    fun onSettingsButtonClicked() {
        navigateTo(ProfileFragmentDirections.actionProfileFragmentToSettingsFragment())
    }

    fun onAboutButtonClicked() {
        navigateTo(ProfileFragmentDirections.actionProfileFragmentToAboutFragment())
    }



}