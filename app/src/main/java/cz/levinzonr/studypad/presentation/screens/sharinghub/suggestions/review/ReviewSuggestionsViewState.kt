package cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.review

import cz.levinzonr.studypad.presentation.events.Event
import cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.SuggestionsModels

data class ReviewSuggestionsViewState(
    val suggestions: List<SuggestionsModels.SuggestionItem> = listOf()
)

data class Conflict(
    val chosen: SuggestionsModels.SuggestionItem,
    val conflicted: SuggestionsModels.SuggestionItem
)