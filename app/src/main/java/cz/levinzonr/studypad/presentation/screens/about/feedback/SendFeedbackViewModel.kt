package cz.levinzonr.studypad.presentation.screens.about.feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.LeaveFeedbackInteractor
import cz.levinzonr.studypad.presentation.base.BaseViewModel

class SendFeedbackViewModel(private val sendFeedbackInteractor: LeaveFeedbackInteractor) : BaseViewModel() {

    private val _stateLiveData = MutableLiveData<SendFeedbackViewState>()
    init {
        _stateLiveData.postValue(SendFeedbackViewState())
    }

    val stateLiveData: LiveData<SendFeedbackViewState>
        get() = _stateLiveData

    fun onSendFeedbackButtonClicked(message: String) {
        sendFeedbackInteractor.executeWithInput(message) {
            onComplete { navigateBack() }
            onError { handleApplicationError(it) }
        }
    }

    fun onMessageChanged(message: String) {
        val state = _stateLiveData.value ?: SendFeedbackViewState()
        _stateLiveData.postValue(state.copy(sendButtonEnabled = message.isNotEmpty()))
    }
}