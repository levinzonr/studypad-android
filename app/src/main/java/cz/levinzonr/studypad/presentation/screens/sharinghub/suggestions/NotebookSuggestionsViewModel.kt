package cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.presentation.base.BaseViewModel

class NotebookSuggestionsViewModel(val suggestions: List<PublishedNotebook.Modification>, val notes: List<Note>) : BaseViewModel() {

    val suggestionsLiveData: MutableLiveData<List<SuggestionsModels.SuggestionItem>> = MutableLiveData()

    init {
        val list = List(suggestions.size) {
            val suggestion = suggestions[it]
            SuggestionsModels.SuggestionItem(suggestion, notes.firstOrNull { note -> suggestion.sourceId == note.id})
        }
        suggestionsLiveData.postValue(list)
    }

    fun onReviewButtonClicked(notebooid:String) {
        val list = suggestionsLiveData.value ?: listOf()
        navigateTo(NotebookSuggestionsFragmentDirections.actionNotebookSuggestionsFragmentToReviewSuggestionsFragment(suggestions.toTypedArray(),notebooid, notes.toTypedArray()))
    }


}