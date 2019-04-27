package cz.levinzonr.studypad.presentation.screens.challenges.write

import cz.levinzonr.studypad.domain.interactors.SaveChallengeInteractor
import cz.levinzonr.studypad.domain.repository.NotesRepository
import cz.levinzonr.studypad.presentation.screens.challenges.challenge.ChallengeViewModel
import cz.levinzonr.studypad.presentation.screens.challenges.setup.SetupChallengeViewState

class WriteChallengeViewModel(saveChallengeInteractor: SaveChallengeInteractor,
                              notesRepository: NotesRepository, challengeSetup: SetupChallengeViewState
) : ChallengeViewModel<String>(saveChallengeInteractor, notesRepository, challengeSetup) {


    override fun validateAnswer(userAnswer: String) : Boolean {
        val answer = notes[currentBaseState.currentPosition].answer.trim()
        return userAnswer.trim().equals(answer, true)
    }


}