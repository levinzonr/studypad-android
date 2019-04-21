package cz.levinzonr.studypad.presentation.screens.challenges.setup

import android.os.Parcelable
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengeType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SetupChallengeViewState (
    val currentType: ChallengeType = ChallengeType.None,
    val titleFirst: Boolean = true,
    val shuffle: Boolean = true,
    val notebook: Notebook? = null
) : Parcelable

