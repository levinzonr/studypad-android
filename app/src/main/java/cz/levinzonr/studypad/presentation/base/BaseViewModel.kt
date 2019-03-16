package cz.levinzonr.studypad.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import cz.levinzonr.studypad.call
import cz.levinzonr.studypad.presentation.events.Event
import cz.levinzonr.studypad.presentation.events.SimpleEvent
import cz.levinzonr.studypad.presentation.screens.NavigationEvent

abstract class BaseViewModel : ViewModel() {

    val errorLiveData: LiveData<Event<String>>
        get() = errorEventLiveData

    val loadingLiveData: LiveData<Boolean>
        get() = loadingEventLiveData


    val navigationLiveData: LiveData<Event<NavigationEvent>>
        get() = navigationEventLiveData

    protected fun postError(message: String) {
        toggleLoading(false)
        errorEventLiveData.postValue(Event(message))
    }

    protected fun toggleLoading(loading: Boolean) {
        loadingEventLiveData.postValue(loading)
    }

    protected fun navigateBack() {
        navigationEventLiveData.call(NavigationEvent.NavigateBack)
    }

    protected fun navigateTo(navDirections: NavDirections) {
        navigationEventLiveData.call(NavigationEvent.NavigateTo(navDirections))
    }

    protected fun navigateTo(id: Int) {
        navigationEventLiveData.call(NavigationEvent.NavigateById(id))
    }

    private val loadingEventLiveData = MutableLiveData<Boolean>()
    private val errorEventLiveData = MutableLiveData<Event<String>>()
    private val navigationEventLiveData = MutableLiveData<Event<NavigationEvent>>()


}