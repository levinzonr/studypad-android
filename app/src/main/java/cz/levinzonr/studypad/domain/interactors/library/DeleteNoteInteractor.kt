package cz.levinzonr.studypad.domain.interactors.library

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.repository.NotesRepository

class DeleteNoteInteractor(private val repository: NotesRepository) : BaseInputInteractor<Long, Unit>() {


    override suspend fun executeOnBackground(input: Long) {
        repository.deleteNote(input)
    }
}