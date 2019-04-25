package cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.sharinghub.SubmitReviewInteractor
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.indexOfFirstOrNull
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import timber.log.Timber

class ReviewSuggestionsViewModel(
    notes: List<Note>,
    suggestions: List<PublishedNotebook.Modification>,
    private val submitReviewInteractor: SubmitReviewInteractor
) : BaseViewModel() {

    val suggestionsLiveData = MutableLiveData<List<SuggestionsModels.SuggestionItem>>()

    init {
        val list = List(suggestions.size) {
            val suggestion = suggestions[it]
            SuggestionsModels.SuggestionItem(suggestion, notes.firstOrNull { note -> suggestion.sourceId == note.id})
        }
        suggestionsLiveData.postValue(list)
    }

    fun onApproveSuggestionClicked(suggestionItem: SuggestionsModels.SuggestionItem) {
        Timber.d("Sugg $suggestionItem")
        val suggestions = suggestionsLiveData.value?.toMutableList() ?: mutableListOf()
        val existed = suggestions.indexOfFirstOrNull { suggestionItem == it } ?: return
        Timber.d("ex $existed")

        val indexOfConflict = suggestions
            .filter { it.approved && it.suggestion.sourceId != null }
            .firstOrNull {
                it.suggestion.sourceId == suggestionItem.suggestion.sourceId && it.suggestion.id != suggestionItem.suggestion.id

        }

        if (indexOfConflict != null && indexOfConflict.approved) {
            Timber.d("Confli $indexOfConflict")

        } else  {
            suggestions[existed] = suggestionItem.copy(state = SuggestionsModels.SuggestionState.Approved)

        }
        suggestionsLiveData.postValue(suggestions)
    }

    fun onRejectSuggestionClicked(suggestionItem: SuggestionsModels.SuggestionItem) {
        val index = suggestionsLiveData.value?.indexOfFirstOrNull { suggestionItem == it } ?: return
        val list = suggestionsLiveData.value?.toMutableList() ?: mutableListOf()
        list[index] = suggestionItem.copy(state = SuggestionsModels.SuggestionState.Rejected)
        suggestionsLiveData.postValue(list)
    }


    fun onSumbitReviewBtnClicked(notebbokId: String) {
        val approved = suggestionsLiveData.value?.filter { it.approved }?.map { it.suggestion.id }
        val rejected = suggestionsLiveData.value?.filter { it.rejected }?.map { it.suggestion.id }
        if (rejected != null && approved != null) {
            val input = SubmitReviewInteractor.Input(notebbokId, rejected, approved)
            submitReviewInteractor.executeWithInput(input) {
                onComplete { navigateBack() }
                onError { handleApplicationError(it) }
            }
        }
    }
}