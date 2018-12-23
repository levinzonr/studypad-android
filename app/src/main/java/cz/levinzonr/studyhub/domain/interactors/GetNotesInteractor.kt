package cz.levinzonr.studyhub.domain.interactors

import cz.levinzonr.studyhub.domain.Note
import cz.levinzonr.studyhub.domain.repository.NotesRepository

class GetNotesInteractor(private val notesRepository: NotesRepository) : BaseInteractor<List<Note>>() {

    override suspend fun executeOnBackground(): List<Note> {
        return notesRepository.getNotesFromNotebook(1)
    }


}