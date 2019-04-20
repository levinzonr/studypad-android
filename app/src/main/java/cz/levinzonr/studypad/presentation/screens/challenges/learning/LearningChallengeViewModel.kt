package cz.levinzonr.studypad.presentation.screens.challenges.learning

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import cz.levinzonr.studypad.domain.repository.NotesRepository
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengesModels
import cz.levinzonr.studypad.presentation.screens.challenges.setup.SetupChallengeViewState

class LearningChallengeViewModel(
    private val notesRepository: NotesRepository,
    challengeSetup: SetupChallengeViewState
) : BaseViewModel() {

    val questionsLiveData: LiveData<List<ChallengesModels.NoteItem>>

    init {
        questionsLiveData = Transformations.map(notesRepository.notesLiveData(challengeSetup.notebook!!.id)) {
            val notes = it.map {
                if (challengeSetup.titleFirst)
                    ChallengesModels.NoteItem(it.title ?: "", it.content ?: "")
                else ChallengesModels.NoteItem(it.content ?: "", it.title ?: "")
            }
            return@map if (challengeSetup.shuffle) notes.shuffled() else notes
        }
    }

    fun onLeaveChallengeConfirmed() {
        navigateBack()
    }


}