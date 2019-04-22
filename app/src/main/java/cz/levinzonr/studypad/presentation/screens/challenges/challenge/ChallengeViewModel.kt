package cz.levinzonr.studypad.presentation.screens.challenges.challenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import cz.levinzonr.studypad.call
import cz.levinzonr.studypad.callIf
import cz.levinzonr.studypad.domain.interactors.SaveChallengeInteractor
import cz.levinzonr.studypad.domain.repository.NotesRepository
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.Event
import cz.levinzonr.studypad.presentation.events.SingleLiveEvent
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengeStats
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengesFragmentDirections
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengesModels
import cz.levinzonr.studypad.presentation.screens.challenges.learning.LearningChallengeFragment
import cz.levinzonr.studypad.presentation.screens.challenges.learning.LearningChallengeFragmentDirections
import cz.levinzonr.studypad.presentation.screens.challenges.setup.SetupChallengeViewState
import timber.log.Timber

class ChallengeViewModel(
    private val saveChallengeInteractor: SaveChallengeInteractor,
    private val notesRepository: NotesRepository,
    private val challengeSetup: SetupChallengeViewState
) : BaseViewModel() {

    val questionsLiveData: LiveData<List<ChallengesModels.NoteItem>>

    val repeatEvent: MutableLiveData<SingleLiveEvent> = MutableLiveData()
    val completedEvent: MutableLiveData<Event<ChallengeStats>> = MutableLiveData()

    private val answeredCorrect = ArrayList<ChallengesModels.NoteItem>()
    private val answeredWrong = ArrayList<ChallengesModels.NoteItem>()


    init {
        questionsLiveData = Transformations.map(notesRepository.notesLiveData(challengeSetup.notebook!!.id)) {
            val notes = it.map {
                if (challengeSetup.titleFirst)
                    ChallengesModels.NoteItem(it.title ?: "", it.content ?: "")
                else ChallengesModels.NoteItem(it.content ?: "", it.title ?: "")
            }
            Timber.d("Notes: $it")
            return@map if (challengeSetup.shuffle) notes.shuffled() else notes
        }
    }

    fun onLeaveChallengeConfirmed() {
        navigateBack()
    }

    fun repeatChallenge() {
        repeatEvent.call()
    }

    fun addCorrectAnswer(noteItem: ChallengesModels.NoteItem) {
        answeredCorrect.add(noteItem)
    }

    fun addWrongAnswer(noteItem: ChallengesModels.NoteItem) {
        answeredWrong.add(noteItem)
    }


    fun onChallengeComplete(showStats: Boolean = true) {
        Timber.d("showState: $showStats")
        val all = questionsLiveData.value ?: listOf()
        val stats = ChallengeStats(challengeSetup.currentType, all.count(), answeredCorrect.count(), answeredWrong.count())
        saveChallengeInteractor.executeWithInput(SaveChallengeInteractor.Input(challengeSetup, stats)) {
            onComplete {
                Timber.d("Saved")
                if (showStats) completedEvent.call(stats)
            }
        }


    }


}