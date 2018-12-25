package cz.levinzonr.studypad.domain.repository

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


    override suspend fun deleteNotebook(id: Long) {
        return api.deleteNotebook(id).await()
    }
}