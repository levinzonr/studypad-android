package cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.presentation.base.BaseViewModel

class NotebookSuggestionsViewModel(suggestions: List<PublishedNotebook.Modification>, notes: List<Note>) : BaseViewModel() {

    val suggestionsLiveData: MutableLiveData<List<SuggestionsModels.SuggestionItem>> = MutableLiveData()

    init {
        val list = List(suggestions.size) {
            val suggestion = suggestions[it]
            SuggestionsModels.SuggestionItem(suggestion, notes.firstOrNull { note -> suggestion.sourceId == note.id})
        }
        suggestionsLiveData.postValue(list)
    }

    fun onReviewButtonClicked() {
        val list = suggestionsLiveData.value ?: listOf()
        navigateTo(NotebookSuggestionsFragmentDirections.actionNotebookSuggestionsFragmentToReviewSuggestionsFragment(list.toTypedArray()))
    }


}