package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.domain.repository.NotebookRepository

class PostNotebookInteractor(private val notebookRepository: NotebookRepository) : BaseInteractor<Notebook>() {


    data class Input(val name: String)

    var input: Input? = null

    override suspend fun executeOnBackground(): Notebook {
        return notebookRepository.createNotebook(input!!.name)
    }
}