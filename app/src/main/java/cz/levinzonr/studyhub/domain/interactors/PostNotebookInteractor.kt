package cz.levinzonr.studyhub.domain.interactors

import cz.levinzonr.studyhub.domain.Notebook
import cz.levinzonr.studyhub.domain.repository.NotebookRepository

class PostNotebookInteractor(private val notebookRepository: NotebookRepository) : BaseInteractor<Notebook>() {


    data class Input(val name: String)

    var input: Input? = null

    override suspend fun executeOnBackground(): Notebook {
        return notebookRepository.createNotebook(input!!.name)
    }
}