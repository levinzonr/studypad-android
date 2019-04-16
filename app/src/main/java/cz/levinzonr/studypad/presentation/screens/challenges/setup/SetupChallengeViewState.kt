package cz.levinzonr.studypad.presentation.screens.challenges.setup

import cz.levinzonr.studypad.domain.models.Notebook

data class SetupChallengeViewState (
    val currentType: ChallengeType = ChallengeType.None,
    val titleFirst: Boolean = true,
    val shuffle: Boolean = true,
    val notebook: Notebook? = null
)

enum class ChallengeType {
    None, Write, Learn, Selfcheck
}