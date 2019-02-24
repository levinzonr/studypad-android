package cz.levinzonr.studypad.domain.interactors.library

import cz.levinzonr.studypad.domain.interactors.BaseInteractor
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.repository.NotesRepository

class UpdateNoteInteractor(private val repository: NotesRepository) : BaseInteractor<Note>() {

    data class Input(val id: Long, val name: String, val content: String)

    var input: Input =
        Input(-1, "", "")

    override suspend fun executeOnBackground(): Note {
        return repository.updateNote(input.id, input.name, input.content)
    }
}