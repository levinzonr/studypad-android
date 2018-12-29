package cz.levinzonr.studypad.domain.repository

import androidx.lifecycle.LiveData
import cz.levinzonr.studypad.domain.models.Notebook

interface NotebookRepository {

    suspend fun getNotebooks() : List<Notebook>

    suspend fun createNotebook(name: String) : Notebook

    suspend fun updateNotebook(id: Long, name: String) : Notebook

    suspend fun deleteNotebook(id: Long)

    fun notebooksLiveData() : LiveData<List<Notebook>>

}