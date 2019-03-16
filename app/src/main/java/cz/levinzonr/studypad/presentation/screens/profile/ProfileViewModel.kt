package cz.levinzonr.studypad.presentation.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.levinzonr.studypad.domain.interactors.GetUserProfileInteractor
import cz.levinzonr.studypad.domain.interactors.keychain.LogoutInteractor
import cz.levinzonr.studypad.domain.models.UserProfile
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.SimpleEvent

class ProfileViewModel(
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
                userLoggedOutEvent.postValue(SimpleEvent())
            }
        }
    }

    fun loadProfile() {
        getUserProfileInteractor.execute {
            onComplete { profileLiveData.postValue(it) }
        }
    }


}