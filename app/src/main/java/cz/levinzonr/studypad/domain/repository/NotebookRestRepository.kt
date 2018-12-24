package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.data.CreateNotebookRequest
import cz.levinzonr.studypad.domain.Notebook
import cz.levinzonr.studypad.rest.Api

class NotebookRestRepository(private val api: Api) : NotebookRepository {

    override suspend fun getNotebooks(): List<Notebook> {
        return api.getNotebooks().await()
    }

    override suspend fun createNotebook(name: String): Notebook {
        return api.postNotebook(CreateNotebookRequest(name)).await()
    }
}