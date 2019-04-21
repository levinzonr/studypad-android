package cz.levinzonr.studypad.presentation.screens.challenges

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class ChallengesModels {

    @Parcelize
    data class NoteItem(
        val question: String,
        val answer: String,
        var showAnswer: Boolean = false
    ) : Parcelable
}

enum class ChallengeType {
    None, Write, Learn, Selfcheck
}