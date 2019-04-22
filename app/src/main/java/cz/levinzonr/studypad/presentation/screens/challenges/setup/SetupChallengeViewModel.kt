package cz.levinzonr.studypad.presentation.screens.challenges.setup

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengeType

class SetupChallengeViewModel : BaseViewModel() {

    val viewStateLiveData : MutableLiveData<SetupChallengeViewState> = MutableLiveData()
    init {
        viewStateLiveData.postValue(SetupChallengeViewState())
    }

    fun onChallengeTypeSelected(challengeType: ChallengeType) {
        val currentState = viewStateLiveData.value ?: SetupChallengeViewState()
        val reverted = if (currentState.currentType == challengeType) ChallengeType.None else challengeType
        viewStateLiveData.postValue(currentState.copy(reverted))
    }

    fun onShuffleModeChange(newValue: Boolean) {
        val currentState = viewStateLiveData.value ?: SetupChallengeViewState()
        viewStateLiveData.postValue(currentState.copy(shuffle = newValue))
    }

    fun onTitleFirstModeChange(newValue: Boolean) {
        val currentState = viewStateLiveData.value ?: SetupChallengeViewState()
        viewStateLiveData.postValue(currentState.copy(titleFirst = newValue
        ))
    }

    fun onNotebookSelected(notebook: Notebook) {
        val currentState = viewStateLiveData.value ?: SetupChallengeViewState()
        viewStateLiveData.postValue(currentState.copy(notebook = notebook))
    }

    fun onStartChallengeClicked() {
        val currentState = viewStateLiveData.value ?: SetupChallengeViewState()
        navigateTo(SetupChallengeFragmentDirections.actionSetupChallengeFragmentToChallengeActivity(currentState))
    }
}