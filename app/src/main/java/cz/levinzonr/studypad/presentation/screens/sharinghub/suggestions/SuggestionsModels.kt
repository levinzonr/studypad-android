package cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions

import android.os.Parcelable
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import kotlinx.android.parcel.Parcelize

object SuggestionsModels {

    @Parcelize
    data class SuggestionItem(
        val suggestion: PublishedNotebook.Modification,
        val sourceNote: Note? = null,
        val approved: Boolean = false,
        val rejected: Boolean = false
    ) : Parcelable

    data class SubmitedReview(
        val approved: List<PublishedNotebook.Modification>,
        val rejected: List<PublishedNotebook.Modification>)
}