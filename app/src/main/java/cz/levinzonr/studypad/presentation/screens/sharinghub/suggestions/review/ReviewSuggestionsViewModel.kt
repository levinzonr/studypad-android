package cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.review

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.sharinghub.SubmitReviewInteractor
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.indexOfFirstOrNull
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.Event
import cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.SuggestionsModels
import timber.log.Timber

class ReviewSuggestionsViewModel(
    notes: List<Note>,
    suggestions: List<PublishedNotebook.Modification>,
    private val submitReviewInteractor: SubmitReviewInteractor
) : BaseViewModel() {

    val suggestionsLiveData = MutableLiveData<ReviewSuggestionsViewState>()

    private val currentViewState: ReviewSuggestionsViewState
        get() = suggestionsLiveData.value ?: ReviewSuggestionsViewState()


    val conflictedSuggestion: MutableLiveData<Event<Conflict>?> = MutableLiveData()


    init {
        conflictedSuggestion.postValue(null)
        val list = List(suggestions.size) {
            val suggestion = suggestions[it]
            SuggestionsModels.SuggestionItem(
                suggestion,
                notes.firstOrNull { note -> suggestion.sourceId == note.id })
        }
        suggestionsLiveData.postValue(ReviewSuggestionsViewState(suggestions = list))
    }

    fun onApproveSuggestionClicked(suggestionItem: SuggestionsModels.SuggestionItem) {
        Timber.d("Sugg $suggestionItem")
        val suggestions = suggestionsLiveData.value?.suggestions?.toMutableList() ?: mutableListOf()
        val existed = suggestions.indexOfFirstOrNull { suggestionItem == it } ?: return
        Timber.d("ex $existed")

        val alreadyChosen = suggestions
            .filter { it.approved && it.suggestion.sourceId != null }
            .firstOrNull {
                it.suggestion.sourceId == suggestionItem.suggestion.sourceId && it.suggestion.id != suggestionItem.suggestion.id

            }

        Timber.d("Chosd: ${alreadyChosen?.state}")
        if (alreadyChosen != null && alreadyChosen.approved) {
            Timber.d("Conflict :${alreadyChosen.suggestion.author.displayName}")
            Timber.d("Treid: ${suggestionItem.suggestion.author.displayName}")
            conflictedSuggestion.postValue(Event(Conflict(alreadyChosen, suggestionItem)))
        } else {
            suggestions[existed] = when (suggestions[existed].state) {
                is SuggestionsModels.SuggestionState.Default -> suggestions[existed].copy(state = SuggestionsModels.SuggestionState.Approved)
                else -> suggestions[existed].copy(state = SuggestionsModels.SuggestionState.Approved)
            }
            Timber.d("exit: ${suggestions[existed].state}")

        }
        suggestionsLiveData.postValue(currentViewState.copy(suggestions = suggestions))
    }

    fun onRejectSuggestionClicked(suggestionItem: SuggestionsModels.SuggestionItem) {
        val index = currentViewState.suggestions.indexOfFirstOrNull { suggestionItem == it } ?: return
        val list = currentViewState.suggestions.toMutableList()
        if (!list[index].rejected)
            list[index] = suggestionItem.copy(state = SuggestionsModels.SuggestionState.Rejected)
        else
            list[index] = suggestionItem.copy(state = SuggestionsModels.SuggestionState.Default)
        suggestionsLiveData.postValue(currentViewState.copy(suggestions = list))
    }


    fun onSumbitReviewBtnClicked(notebbokId: String) {
        toggleLoading(true)
        val approved = currentViewState.suggestions.filter { it.approved }.map { it.suggestion.id }
        val rejected = currentViewState.suggestions.filter { it.rejected }.map { it.suggestion.id }
        val input = SubmitReviewInteractor.Input(notebbokId, rejected, approved)
        submitReviewInteractor.executeWithInput(input) {
            onComplete {
                toggleLoading(false)
                navigateBack()
            }
            onError { handleApplicationError(it) }
        }
    }
}