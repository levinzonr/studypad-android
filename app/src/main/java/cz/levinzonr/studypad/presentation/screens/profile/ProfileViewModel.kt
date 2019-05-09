package cz.levinzonr.studypad.presentation.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.GetUserProfileInteractor
import cz.levinzonr.studypad.domain.interactors.keychain.LogoutInteractor
import cz.levinzonr.studypad.domain.interactors.keychain.UpdateUserInteractor
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.domain.models.UserProfile
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.SingleLiveEvent
import cz.levinzonr.studypad.presentation.screens.Flow

class ProfileViewModel(
    private val userManager: UserManager,
    private val getUserProfileInteractor: GetUserProfileInteractor,
    private val logoutInteractor: LogoutInteractor
) : BaseViewModel() {


    val profileLiveData = MutableLiveData<ProfieViewState>()

    init {
        loadProfile()
        userManager.getCurrentUserInfo()?.let {
            val state = ProfieViewState(it.displayName, it.university, 0, it.imageUrl)
            profileLiveData.postValue(state)
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
            onComplete {
                val state = ProfieViewState(it.displayName, it.university, it.unreadNotifications, it.photoUrl)
                profileLiveData.postValue(state)
            }
            onError { handleApplicationError(it) }
        }
    }

    fun onSettingsButtonClicked() {
        navigateTo(ProfileFragmentDirections.actionProfileFragmentToSettingsFragment())
    }

    fun onAboutButtonClicked() {
        navigateTo(ProfileFragmentDirections.actionProfileFragmentToAboutFragment())
    }



}