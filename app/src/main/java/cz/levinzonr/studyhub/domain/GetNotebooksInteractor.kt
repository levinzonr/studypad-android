package cz.levinzonr.studyhub.domain

import cz.levinzonr.studyhub.domain.repository.MockNotebookRepository

class GetNotebooksInteractor : ResultInteractor<List<Notebook>>(){


    override suspend fun execute(): List<Notebook> {
        val repo = MockNotebookRepository()
        val result = repo.getNotebooks()
        return result
    }
}