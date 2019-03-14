package cz.levinzonr.studypad.domain.repository

import androidx.lifecycle.LiveData
import cz.levinzonr.studypad.data.NotebooksResponse
import cz.levinzonr.studypad.domain.models.Notebook

interface NotebookRepository {

    suspend fun getNotebooks() : List<NotebooksResponse>

    suspend fun createNotebook(name: String) : Notebook

    suspend fun updateNotebook(id: String, name: String) : Notebook

    suspend fun deleteNotebook(id: String)

    suspend fun importNotebook(id: String) : Notebook

    fun notebooksLiveData() : LiveData<List<Notebook>>

    fun getStoredNotebooks() : List<Notebook>

    fun deleteLocally(id: String)
}