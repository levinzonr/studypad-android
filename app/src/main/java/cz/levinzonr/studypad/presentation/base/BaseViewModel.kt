package cz.levinzonr.studypad.presentation.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import cz.levinzonr.studypad.call
import cz.levinzonr.studypad.domain.managers.TranslationManager
import cz.levinzonr.studypad.domain.models.ApplicationError
import cz.levinzonr.studypad.domain.models.ViewError
import cz.levinzonr.studypad.presentation.events.Event
import cz.levinzonr.studypad.presentation.events.SingleLiveEvent
import cz.levinzonr.studypad.presentation.screens.Flow
import cz.levinzonr.studypad.presentation.screens.NavigationEvent
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

abstract class BaseViewModel : ViewModel(), KoinComponent {

    private val translationManager: TranslationManager by inject()

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

    protected fun showNetworkUnavailabe() {
        val currentState = viewStateLiveData.value ?: BaseViewState()
        viewStateLiveData.postValue(currentState.copy(isLoading = false, networkError = SingleLiveEvent()))
    }

    protected open fun handleApplicationError(applicationError: ApplicationError) {
        when(applicationError) {
            is ApplicationError.NetworkError -> showNetworkUnavailabe()
            is ApplicationError.ApiError -> showError(ViewError.ToastError(applicationError.message))
            is ApplicationError.GenericError -> showError(ViewError.ToastError(applicationError.exception.localizedMessage))
        }
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

    protected fun string(id: Int) : String = translationManager.getResourceById(id)


}