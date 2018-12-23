package cz.levinzonr.studyhub.domain.interactors

import cz.levinzonr.studyhub.domain.Notebook
import cz.levinzonr.studyhub.domain.repository.NotebookRepository

class GetNotebooksInteractor(private val repository: NotebookRepository) : BaseInteractor<List<Notebook>>(){

    override suspend fun executeOnBackground(): List<Notebook> {
        return repository.getNotebooks()
    }
}