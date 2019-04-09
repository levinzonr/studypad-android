package cz.levinzonr.studypad.domain.repository

import androidx.lifecycle.LiveData
import cz.levinzonr.studypad.domain.models.SearchEntry

interface SearchHistoryRepository {

    fun saveSearchEntry(searchEntry: SearchEntry)

    fun getAllSearchEntries() : LiveData<List<SearchEntry>>

    fun clear()

}