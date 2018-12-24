package cz.levinzonr.studyhub.domain.repository

import cz.levinzonr.studyhub.data.CreateNotebookRequest
import cz.levinzonr.studyhub.domain.Notebook
import cz.levinzonr.studyhub.rest.Api

class NotebookRestRepository(private val api: Api) : NotebookRepository {

    override suspend fun getNotebooks(): List<Notebook> {
        return api.getNotebooks().await()
    }

    override suspend fun createNotebook(name: String): Notebook {
        return api.postNotebook(CreateNotebookRequest(name)).await()
    }
}