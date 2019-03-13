package cz.levinzonr.studypad.domain.interactors.library

import cz.levinzonr.studypad.domain.interactors.BaseInteractor
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.repository.NotesRepository

class PostNoteInteractor(private val repository: NotesRepository) : BaseInteractor<Note>(){

    data class Input(val notebookId: String, val title: String, val content: String)

    var input: Input? = null

    override suspend fun executeOnBackground(): Note {
        return repository.createNote(input!!.notebookId, input!!.title, input!!.content)
    }
}