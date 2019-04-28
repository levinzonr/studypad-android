package cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions

data class SuggestionsViewState(
    val isReviewButtonEnabled: Boolean = false,
    val items: List<SuggestionsModels.SuggestionItem> = listOf()
)