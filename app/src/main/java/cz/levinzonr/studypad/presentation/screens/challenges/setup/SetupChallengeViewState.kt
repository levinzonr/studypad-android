package cz.levinzonr.studypad.presentation.screens.challenges.setup

import android.os.Parcelable
import cz.levinzonr.studypad.domain.models.Notebook
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SetupChallengeViewState (
    val currentType: ChallengeType = ChallengeType.None,
    val titleFirst: Boolean = true,
    val shuffle: Boolean = true,
    val notebook: Notebook? = null
) : Parcelable

enum class ChallengeType {
    None, Write, Learn, Selfcheck
}