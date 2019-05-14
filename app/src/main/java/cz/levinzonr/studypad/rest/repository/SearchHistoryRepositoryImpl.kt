package cz.levinzonr.studypad.rest.repository

import androidx.lifecycle.LiveData
import cz.levinzonr.studypad.domain.models.SearchEntry
import cz.levinzonr.studypad.domain.repository.SearchHistoryRepository
import cz.levinzonr.studypad.storage.database.AppDatabase
import timber.log.Timber

class SearchHistoryRepositoryImpl(val appDatabase: AppDatabase) :
    SearchHistoryRepository {

    override fun saveSearchEntry(searchEntry: SearchEntry) {
        val entry = searchEntry.copy(lastlyUsed = System.currentTimeMillis())
        Timber.d("3 Hello from thread ${Thread.currentThread().name}")

        appDatabase.searchEntryDao().put(entry)
    }

    override fun getAllSearchEntries(): LiveData<List<SearchEntry>> {
        return appDatabase.searchEntryDao().getAll()
    }

    override fun clear() {
        appDatabase.searchEntryDao().deleteAll()
    }
}