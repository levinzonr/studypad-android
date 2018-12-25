package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.rest.Api

class NoteRestRepository(private val api: Api) : NotesRepository {

    override suspend fun getNotesFromNotebook(notebookId: Long): List<Note> {
        return api.getNotesFromNotebook(notebookId).await()
    }


}