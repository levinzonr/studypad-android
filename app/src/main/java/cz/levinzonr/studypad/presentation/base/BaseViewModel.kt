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

/**
 * Base ViewModel that is used for others to extend
 *
 */
abstract class BaseViewModel : ViewModel(), KoinComponent {

    /**
     * Android resource abstrac that is used to get the translation directly from the viewModel
     */
    private val translationManager: TranslationManager by inject()


    /**
     * LiveData that manges Navigation Events
     */
    private val navigationEventLiveData = MutableLiveData<Event<NavigationEvent>>()

    /**
     * LiveData that manages base view state
     */
    private val viewStateLiveData = MutableLiveData<BaseViewState>()

    init {
        viewStateLiveData.postValue(BaseViewState())
    }

    val viewStateObservalbe: LiveData<BaseViewState>
        get() = viewStateLiveData



    val navigationLiveData: LiveData<Event<NavigationEvent>>
        get() = navigationEventLiveData


    /**
     * Helper method to post an error  by updating base view state
     */
    protected fun showError(viewError: ViewError) {
        val currentState = viewStateLiveData.value ?: BaseViewState()
        viewStateLiveData.postValue(currentState.copy( isLoading =  false, error = Event(viewError)))
    }

    /**
     * Helper method to toggle loading by updating base view state
     */
    protected fun toggleLoading(loading: Boolean) {
        val currentState = viewStateLiveData.value ?: BaseViewState()
        viewStateLiveData.postValue(currentState.copy(isLoading = loading))
    }

    /**
     * Helper method to reflect bad connectivity  by updating base view state
     */
    protected fun showNetworkUnavailabe() {
        val currentState = viewStateLiveData.value ?: BaseViewState()
        viewStateLiveData.postValue(currentState.copy(isLoading = false, networkError = SingleLiveEvent()))
    }


    /**
     * Converts Application Error in to View Error that can be presented by Fragmetns
     */
    protected open fun handleApplicationError(applicationError: ApplicationError) {
        when(applicationError) {
            is ApplicationError.NetworkError -> showNetworkUnavailabe()
            is ApplicationError.ApiError -> showError(ViewError.ToastError(applicationError.message))
            is ApplicationError.GenericError -> showError(ViewError.ToastError(applicationError.exception.localizedMessage))
        }
    }


    /**
     * Helper method to post a navigate back event
     */
    protected fun navigateBack() {
        navigationEventLiveData.call(NavigationEvent.NavigateBack)
    }

    /**
     * Helper method to perform a navigation event
     */
    protected fun navigateTo(navDirections: NavDirections) {
        navigationEventLiveData.call(NavigationEvent.NavigateTo(navDirections))
    }

    protected fun changeFlow(flow: Flow) {
        navigationEventLiveData.call(NavigationEvent.ChangeFlow(flow))
    }

    protected fun string(id: Int) : String = translationManager.getResourceById(id)


}