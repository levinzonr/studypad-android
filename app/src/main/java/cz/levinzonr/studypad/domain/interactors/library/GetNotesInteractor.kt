package cz.levinzonr.studypad.domain.interactors.library

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.repository.NotesRepository

class GetNotesInteractor(private val notesRepository: NotesRepository) : BaseInputInteractor<String, List<Note>>() {

    override suspend fun executeOnBackground(input: String): List<Note> {
        return notesRepository.getNotesFromNotebook(input)
    }


}