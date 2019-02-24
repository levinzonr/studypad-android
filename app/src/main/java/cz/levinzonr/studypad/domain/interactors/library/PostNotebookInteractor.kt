package cz.levinzonr.studypad.domain.interactors.library

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.domain.repository.NotebookRepository

class PostNotebookInteractor(private val notebookRepository: NotebookRepository) : BaseInputInteractor<String, Notebook>() {



    override suspend fun executeOnBackground(input:  String): Notebook {
        return notebookRepository.createNotebook(input)
    }
}