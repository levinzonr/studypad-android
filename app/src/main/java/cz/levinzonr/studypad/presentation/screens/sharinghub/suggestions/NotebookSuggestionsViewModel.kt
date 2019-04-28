package cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.sharinghub.GetPendingSuggestionsInteractor
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.presentation.base.BaseViewModel

class NotebookSuggestionsViewModel(
    val notebookId: String,
    val authoredByMe: Boolean,
    val notes: List<Note>,
    private val getPendingSuggestionsInteractor: GetPendingSuggestionsInteractor) : BaseViewModel() {

    val viewState: MutableLiveData<SuggestionsViewState> = MutableLiveData()


    private lateinit var suggestions: List<PublishedNotebook.Modification>
    init {
        viewState.postValue(SuggestionsViewState())
        refresh()
    }

    fun onReviewButtonClicked() {
        navigateTo(NotebookSuggestionsFragmentDirections.actionNotebookSuggestionsFragmentToReviewSuggestionsFragment(
            suggestions.toTypedArray(), notebookId, notes.toTypedArray())
        )
    }

    private fun transformAndPost(mods: List<PublishedNotebook.Modification>) {
        suggestions = mods
        val list = List(suggestions.size) {
            val suggestion = suggestions[it]
            SuggestionsModels.SuggestionItem(
                suggestion,
                notes.firstOrNull { note -> suggestion.sourceId == note.id })
        }
        viewState.postValue(viewState.value?.copy(isReviewButtonEnabled = authoredByMe && list.isNotEmpty(), items = list))
    }


    fun refresh() {
        getPendingSuggestionsInteractor.executeWithInput(notebookId) {
            onComplete { transformAndPost(it) }
            onError { handleApplicationError(it) }
        }
    }


}