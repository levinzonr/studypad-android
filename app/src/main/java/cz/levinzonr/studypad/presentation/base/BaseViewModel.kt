package cz.levinzonr.studypad.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import cz.levinzonr.studypad.call
import cz.levinzonr.studypad.domain.models.ApplicationError
import cz.levinzonr.studypad.domain.models.ViewError
import cz.levinzonr.studypad.presentation.events.Event
import cz.levinzonr.studypad.presentation.screens.Flow
import cz.levinzonr.studypad.presentation.screens.NavigationEvent

abstract class BaseViewModel : ViewModel() {

    private val navigationEventLiveData = MutableLiveData<Event<NavigationEvent>>()
    private val viewStateLiveData = MutableLiveData<BaseViewState>()

    init {
        viewStateLiveData.postValue(BaseViewState())
    }

    val viewStateObservalbe: LiveData<BaseViewState>
        get() = viewStateLiveData




    val navigationLiveData: LiveData<Event<NavigationEvent>>
        get() = navigationEventLiveData

    protected fun showError(viewError: ViewError) {
        val currentState = viewStateLiveData.value ?: BaseViewState()
        viewStateLiveData.postValue(currentState.copy( isLoading =  false, error = Event(viewError)))
    }

    protected fun toggleLoading(loading: Boolean) {
        val currentState = viewStateLiveData.value ?: BaseViewState()
        viewStateLiveData.postValue(currentState.copy(isLoading = loading))
    }


    protected fun navigateBack() {
        navigationEventLiveData.call(NavigationEvent.NavigateBack)
    }

    protected fun navigateTo(navDirections: NavDirections) {
        navigationEventLiveData.call(NavigationEvent.NavigateTo(navDirections))
    }

    protected fun changeFlow(flow: Flow) {
        navigationEventLiveData.call(NavigationEvent.ChangeFlow(flow))
    }


}