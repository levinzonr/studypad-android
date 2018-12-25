package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.repository.NotesRepository

class GetNotesInteractor(private val notesRepository: NotesRepository) : BaseInteractor<List<Note>>() {

    data class Input(val id: Long)

    var input: Input? = null

    override suspend fun executeOnBackground(): List<Note> {
        return notesRepository.getNotesFromNotebook(input!!.id)
    }


}