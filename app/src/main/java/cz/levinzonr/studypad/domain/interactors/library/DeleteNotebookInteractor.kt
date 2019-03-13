package cz.levinzonr.studypad.domain.interactors.library

import cz.levinzonr.studypad.domain.interactors.BaseInteractor
import cz.levinzonr.studypad.domain.repository.NotebookRepository

class DeleteNotebookInteractor(private val repository: NotebookRepository) : BaseInteractor<Unit>(){


    data class Input(val id: String)

    var input: Input? = null

    override suspend fun executeOnBackground() {
        input?.let {
            repository.deleteNotebook(it.id)
        }
    }
}