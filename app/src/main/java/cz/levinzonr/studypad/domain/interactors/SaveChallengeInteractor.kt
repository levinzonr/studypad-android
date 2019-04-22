package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.models.ChallengeEntry
import cz.levinzonr.studypad.domain.repository.ChallengesRepository
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengeStats
import cz.levinzonr.studypad.presentation.screens.challenges.setup.SetupChallengeViewState

class SaveChallengeInteractor(private val repository: ChallengesRepository): BaseInputInteractor<SaveChallengeInteractor.Input, Unit>() {

    data class Input(val setup: SetupChallengeViewState, val stats: ChallengeStats)

    override suspend fun executeOnBackground(input: Input) {
        val percentage = (input.stats.correctCount * 100 / input.stats.total)
        val entry = ChallengeEntry(input.setup.currentType, input.setup.shuffle, input.setup.titleFirst, input.setup.notebook!!, percentage)
        repository.saveChallengeEntrty(entry)
    }
}