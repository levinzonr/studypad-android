package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.domain.repository.NotebookRepository

class GetNotebooksInteractor(private val repository: NotebookRepository) : BaseInteractor<List<Notebook>>(){

    override suspend fun executeOnBackground(): List<Notebook> {
        return repository.getNotebooks()
    }
}