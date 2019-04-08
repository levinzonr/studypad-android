package cz.levinzonr.studypad.presentation.screens.sharinghub.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.domain.repository.PublishedNotebookRepository
import cz.levinzonr.studypad.presentation.base.BaseViewModel

class NotebooksSearchViewModel(initialState:  NotebookSearchModels.SearchState?,
                               private val repository: PublishedNotebookRepository) : BaseViewModel() {

    private val _searchStateLiveDate = MutableLiveData<NotebookSearchModels.SearchState>()
    val resultsLiveData : LiveData<List<PublishedNotebook.Feed>> = Transformations.switchMap(_searchStateLiveDate) {
        repository.searchNotebooks(it)
    }


    val searchStateLiveData: LiveData<NotebookSearchModels.SearchState>
        get() = _searchStateLiveDate

    val currentSearchState: NotebookSearchModels.SearchState?
        get() = searchStateLiveData.value

    init {
        val initState = initialState ?: NotebookSearchModels.SearchState()
        _searchStateLiveDate.postValue(initState)
    }

    fun onUniversityOptionChanged(university: University?) {
        currentSearchState?.let { searchState ->
            _searchStateLiveDate.postValue(searchState.copy(university = university))
        }
    }

    fun onCategoriesOptionChanged(list: List<Topic>) {
        currentSearchState?.let { searchState ->
            _searchStateLiveDate.postValue(searchState.copy(topic = list))
        }
    }

    fun onTagsOptionChanged(tag: String, isEnabled: Boolean) {
        currentSearchState?.let { searchState ->
            val tags = searchState.tags.toMutableList().apply { if (isEnabled) add(0, tag) else remove(tag) }
            _searchStateLiveDate.postValue(searchState.copy(tags = tags.toList()))
        }
    }

    fun onQueryChanged(query: String) {
        currentSearchState?.let { searchState ->
            _searchStateLiveDate.postValue(searchState.copy(query = query))
        }
    }


}
