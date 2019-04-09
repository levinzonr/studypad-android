package cz.levinzonr.studypad.domain.managers

import androidx.lifecycle.LiveData
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.models.SearchEntry
import cz.levinzonr.studypad.presentation.screens.sharinghub.search.NotebookSearchModels

interface SearchManager {

    fun performSearch(searchState: NotebookSearchModels.SearchState) : LiveData<List<PublishedNotebook.Feed>>

    fun getRecentSearches() : LiveData<List<SearchEntry>>

}