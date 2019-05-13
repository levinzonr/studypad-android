package cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.review

import cz.levinzonr.studypad.presentation.events.Event
import cz.levinzonr.studypad.presentation.events.SingleLiveEvent
import cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.SuggestionsModels

data class ReviewSuggestionsViewState(
    val suggestions: List<SuggestionsModels.SuggestionItem> = listOf()
)

data class ActionViewState(
    val conflictAppeared: Event<Conflict>? = null,
    val showNextSuggestion: Event<Int>? = null,
    val allDone: SingleLiveEvent? = null
)

data class Conflict(
    val chosen: SuggestionsModels.SuggestionItem,
    val conflicted: SuggestionsModels.SuggestionItem
)