package cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions

import android.os.Parcelable
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

object SuggestionsModels {

    @Parcelize
    data class SuggestionItem(
        val suggestion: PublishedNotebook.Modification,
        val sourceNote: Note? = null,

        @IgnoredOnParcel
        var state: SuggestionState = SuggestionsModels.SuggestionState.Default
    ) : Parcelable {
        val approved: Boolean
            get() = state is SuggestionsModels.SuggestionState.Approved
        val rejected: Boolean
            get() = state is SuggestionsModels.SuggestionState.Rejected
        val conflicted: Boolean
            get() = state is SuggestionsModels.SuggestionState.Conflicted
    }


    sealed class SuggestionState : Serializable {
        object Default: SuggestionState()
        object Approved : SuggestionState()
        object Rejected: SuggestionState()
        data class Conflicted(val indexOfConflicted: Int) : SuggestionState()
    }

    data class SubmitedReview(
        val approved: List<PublishedNotebook.Modification>,
        val rejected: List<PublishedNotebook.Modification>)
}