package cz.levinzonr.studypad.presentation.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.levinzonr.studypad.domain.interactors.LogoutInteractor
import cz.levinzonr.studypad.presentation.events.SimpleEvent

class ProfileViewModel(private val logoutInteractor: LogoutInteractor) : ViewModel() {


    val openLoginEvent: LiveData<SimpleEvent>
        get() = userLoggedOutEvent


    private val userLoggedOutEvent = MutableLiveData<SimpleEvent>()

    fun logout() {

        logoutInteractor.execute {
            onComplete {
                userLoggedOutEvent.postValue(SimpleEvent())
            }
        }
    }


}