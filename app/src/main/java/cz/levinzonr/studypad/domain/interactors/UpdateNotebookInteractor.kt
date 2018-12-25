package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.domain.repository.NotebookRepository

class UpdateNotebookInteractor(private val notebookRepository: NotebookRepository) : BaseInteractor<Notebook>() {

    data class Input(val id: Long, val name: String)

    var input: Input? = null

    override suspend fun executeOnBackground(): Notebook {
        return notebookRepository.updateNotebook(input!!.id, input!!.name)
    }
}