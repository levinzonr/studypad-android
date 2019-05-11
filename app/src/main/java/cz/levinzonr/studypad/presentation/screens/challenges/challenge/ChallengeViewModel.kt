package cz.levinzonr.studypad.presentation.screens.challenges.challenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import cz.levinzonr.studypad.domain.interactors.SaveChallengeInteractor
import cz.levinzonr.studypad.domain.repository.NotesRepository
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.Event
import cz.levinzonr.studypad.presentation.events.SingleLiveEvent
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengeStats
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengesModels
import cz.levinzonr.studypad.presentation.screens.challenges.setup.SetupChallengeViewState
import timber.log.Timber

abstract class ChallengeViewModel<in T>(
    private val saveChallengeInteractor: SaveChallengeInteractor,
    private val notesRepository: NotesRepository,
    private val challengeSetup: SetupChallengeViewState
) : BaseViewModel() {

    val questionsLiveData: LiveData<List<ChallengesModels.NoteItem>>
    val viewState: MutableLiveData<BaseChallengeViewState> = MutableLiveData()

    var challengeCompleted: Boolean = false

    protected val currentBaseState: BaseChallengeViewState
        get() = viewState.value ?: BaseChallengeViewState()

    val notes: List<ChallengesModels.NoteItem>
        get() = questionsLiveData.value ?: listOf()

    private val answeredCorrect = ArrayList<ChallengesModels.NoteItem>()
    private val answeredWrong = ArrayList<ChallengesModels.NoteItem>()


    init {
        viewState.postValue(BaseChallengeViewState())
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
        answeredWrong.clear()
        answeredCorrect.clear()
        viewState.postValue(currentBaseState.copy(repeatChallenge = SingleLiveEvent(), currentPosition = 0))
    }

    private fun addCorrectAnswer(noteItem: ChallengesModels.NoteItem) {
        answeredCorrect.add(noteItem)
    }

    private fun addWrongAnswer(noteItem: ChallengesModels.NoteItem) {
        answeredWrong.add(noteItem)
    }


    fun submitAnswer(answer: T) {
        val item = notes.get(currentBaseState.currentPosition)
        val isCorrect = validateAnswer(answer)
        when {
            isCorrect && !answeredWrong.contains(item) -> {
                // This is first submtion of the answer, mark as correct and move along
                addCorrectAnswer(item)
                proceedWithNext()
            }
            isCorrect && answeredWrong.contains(item) -> {
                // repeated answer, previosly wron ,just move along
                proceedWithNext()
            }
            !isCorrect && !answeredWrong.contains(item) -> {
                // first time fail, add and wait for re-submition
                answeredWrong.add(item)
                viewState.postValue(currentBaseState.copy(answeredWrong = Event(currentBaseState.currentPosition)))

            }
            else -> {
                // Second time fail
                viewState.postValue(currentBaseState.copy(answeredWrong = Event(currentBaseState.currentPosition)))
            }
        }
    }

    private fun proceedWithNext() {
        // Check if there are questiosns left
        if (currentBaseState.currentPosition.inc() >= notes.size) {
           handleChallengeFinish()
        } else {
            viewState.postValue(
                currentBaseState.copy(
                    answeredCorrectly = Event(currentBaseState.currentPosition.inc()),
                    currentPosition = currentBaseState.currentPosition.inc()
                )
            )
        }


    }


    protected abstract fun validateAnswer(answers: T): Boolean

    open fun handleChallengeFinish() {
        challengeCompleted = true
        val stats = ChallengeStats(challengeSetup.currentType, notes.size, answeredCorrect.size, answeredWrong.size)
        saveChallengeInteractor.executeWithInput(SaveChallengeInteractor.Input(challengeSetup, stats)) {
            onComplete { viewState.postValue(currentBaseState.copy(completedEvent = Event(stats))) }
        }
    }


}