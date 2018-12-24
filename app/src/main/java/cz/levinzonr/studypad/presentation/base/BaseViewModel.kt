package cz.levinzonr.studypad.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.levinzonr.studypad.presentation.events.Event

abstract class BaseViewModel : ViewModel() {

    val errorLiveData: LiveData<Event<String>>
        get() = errorEventLiveData

    val loadingLiveData: LiveData<Boolean>
        get() = loadingEventLiveData



    protected fun postError(message: String) {
        toggleLoading(false)
        errorEventLiveData.postValue(Event(message))
    }

    protected fun toggleLoading(loading: Boolean) {
        loadingEventLiveData.postValue(loading)
    }

    private val loadingEventLiveData = MutableLiveData<Boolean>()
    private val errorEventLiveData = MutableLiveData<Event<String>>()
}