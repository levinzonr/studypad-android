package cz.levinzonr.studypad.presentation.screens.challenges.learning

import cz.levinzonr.studypad.domain.interactors.SaveChallengeInteractor
import cz.levinzonr.studypad.domain.repository.NotesRepository
import cz.levinzonr.studypad.presentation.screens.challenges.challenge.ChallengeViewModel
import cz.levinzonr.studypad.presentation.screens.challenges.setup.SetupChallengeViewState

class LearningChallengeViewModel(
    saveChallengeInteractor: SaveChallengeInteractor,
    notesRepository: NotesRepository,
    challengeViewState: SetupChallengeViewState) : ChallengeViewModel<Boolean>(saveChallengeInteractor, notesRepository, challengeViewState) {


    override fun validateAnswer(answers: Boolean): Boolean {
        return answers
    }
}