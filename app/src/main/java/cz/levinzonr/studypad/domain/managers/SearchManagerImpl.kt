package cz.levinzonr.studypad.domain.managers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.models.InteractorResult
import cz.levinzonr.studypad.domain.models.OrderBy
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.models.SearchEntry
import cz.levinzonr.studypad.domain.repository.PublishedNotebookRepository
import cz.levinzonr.studypad.domain.repository.SearchHistoryRepository
import cz.levinzonr.studypad.presentation.screens.sharinghub.search.NotebookSearchModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception

class SearchManagerImpl(
    private val publishedNotebookRepository: PublishedNotebookRepository,
    private val searchHistoryRepository: SearchHistoryRepository
) : SearchManager {

    override fun performSearch(searchState: NotebookSearchModels.SearchState): LiveData<InteractorResult<List<PublishedNotebook.Feed>>> {
        val entry = SearchEntry(
            searchState.query,
            searchState.university,
            searchState.orderBy ?: OrderBy.RECENT,
            searchState.topic,
            searchState.tags
        )
        val liveData: MutableLiveData<InteractorResult<List<PublishedNotebook.Feed>>> = MutableLiveData()
        liveData.postValue(InteractorResult.Loading)

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val items = publishedNotebookRepository.searchNotebooks(searchState)
                    liveData.postValue(InteractorResult.Success(items))
                    searchHistoryRepository.saveSearchEntry(entry)
                } catch (exeception: Exception) {
                    liveData.postValue(InteractorResult.Error(exeception))
                }
            }
        }
        return liveData
    }

    override fun getRecentSearches(): LiveData<List<SearchEntry>> {
        return searchHistoryRepository.getAllSearchEntries()
    }
}