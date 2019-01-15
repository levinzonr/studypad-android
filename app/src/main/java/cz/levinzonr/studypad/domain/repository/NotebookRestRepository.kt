package cz.levinzonr.studypad.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.data.CreateNotebookRequest
import cz.levinzonr.studypad.data.UpdateNotebookRequest
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.rest.Api

class NotebookRestRepository(private val api: Api) : NotebookRepository {

    override suspend fun getNotebooks(): List<Notebook> {
        return api.getNotebooks().await()
    }

    override suspend fun createNotebook(name: String): Notebook {
        return api.postNotebook(CreateNotebookRequest(name)).await()
    }

    override suspend fun updateNotebook(id: Long, name: String) : Notebook {
        return api.updateNotebook(id, UpdateNotebookRequest(name)).await()
    }

    override fun notebooksLiveData(): LiveData<List<Notebook>> {
        return MutableLiveData()
    }

    override suspend fun deleteNotebook(id: Long) {
        return api.deleteNotebook(id).await()
    }

    override fun getStoredNotebooks(): List<Notebook> {
        return listOf()
    }

    override fun deleteLocally(id: Long) {

    }
}