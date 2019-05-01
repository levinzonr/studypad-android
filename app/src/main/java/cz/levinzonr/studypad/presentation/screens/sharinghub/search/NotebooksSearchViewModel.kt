package cz.levinzonr.studypad.presentation.screens.sharinghub.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import cz.levinzonr.studypad.domain.managers.SearchManager
import cz.levinzonr.studypad.domain.models.InteractorResult
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.domain.repository.PublishedNotebookRepository
import cz.levinzonr.studypad.presentation.base.BaseViewModel

class NotebooksSearchViewModel(initialState:  NotebookSearchModels.SearchState?,
                               private val searchManager: SearchManager) : BaseViewModel() {

    private val _searchStateLiveDate = MutableLiveData<NotebookSearchModels.SearchState>()


    val resultsLiveData : LiveData<InteractorResult<List<PublishedNotebook.Feed>>> = Transformations.switchMap(_searchStateLiveDate) {
        if (!it.isDefaultState) {
            searchManager.performSearch(it)
        } else
            MutableLiveData()

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

    fun onTagsOptionChanged(tags: Set<String>) {
        currentSearchState?.let { searchState ->
            _searchStateLiveDate.postValue(searchState.copy(tags = tags.toList()))
        }
    }

    fun onQueryChanged(query: String) {
        currentSearchState?.let { searchState ->
            _searchStateLiveDate.postValue(searchState.copy(query = query))
        }
    }

    fun onNotebookSelected(feed: PublishedNotebook.Feed) {
        navigateTo(NotebooksSearchFragmentDirections.actionGlobalPublishedNotebookDetailFragment(feed.id, feed))
    }

    fun performSearch() {
        currentSearchState?.let {
            _searchStateLiveDate.postValue(it.copy())
        }
    }

    fun onSelectUniversityClicked() {
        navigateTo(NotebooksSearchFragmentDirections.actionGlobalUniversitySelectorFragment(true))
    }

}
