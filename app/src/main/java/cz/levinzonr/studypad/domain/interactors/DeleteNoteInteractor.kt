package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.repository.NotesRepository

class DeleteNoteInteractor(private val repository: NotesRepository) : BaseInteractor<Unit>() {

    data class Input(val id: Long)

    var input: Input? = null

    override suspend fun executeOnBackground() {
        input?.let { repository.deleteNote(it.id) }
    }
}