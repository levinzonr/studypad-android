package cz.levinzonr.studypad.presentation.screens.challenges

import android.content.Context
import android.os.Parcelable
import cz.levinzonr.studypad.R
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
    None, Write, Flashcards, Selfcheck;

    fun getName(context: Context) : String {
        return when(this) {
            Flashcards -> context.getString(R.string.challenges_type_flashcards)
            Selfcheck -> context.getString(R.string.challenges_type_selfcheck)
            else -> context.getString(R.string.challenges_type_write)
        }
    }
    fun getDrawableRes() : Int {
        return when(this) {
            Flashcards -> R.drawable.ic_flashcards_24dp
            Selfcheck -> R.drawable.ic_done_all_black_24dp
            else -> R.drawable.ic_challenges
        }
    }
}

@Parcelize
data class ChallengeStats(val type: ChallengeType, val total: Int, val correctCount: Int, val wrongCount: Int) : Parcelable