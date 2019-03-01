package cz.levinzonr.studypad.domain.interactors.sharinghub

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.domain.repository.NotebookRepository

class ImportPublishedNotebookInteractor(
    private val repository: NotebookRepository) : BaseInputInteractor<String, Notebook>() {

    override suspend fun executeOnBackground(input: String): Notebook {
        return repository.importNotebook(input)
    }
}