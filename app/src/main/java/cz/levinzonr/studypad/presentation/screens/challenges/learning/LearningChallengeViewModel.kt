package cz.levinzonr.studypad.presentation.screens.challenges.learning

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengesModels

class LearningChallengeViewModel(list: List<ChallengesModels.NoteItem>) : BaseViewModel() {

    val stateLiveData: MutableLiveData<List<ChallengesModels.NoteItem>> = MutableLiveData()

    init {
        stateLiveData.postValue(list)
    }
}