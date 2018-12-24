package cz.levinzonr.studyhub.domain.repository

import cz.levinzonr.studyhub.domain.Note
import cz.levinzonr.studyhub.rest.Api

class NoteRestRepository(private val api: Api) : NotesRepository {

    override suspend fun getNotesFromNotebook(notebookId: Long): List<Note> {
        return api.getNotesFromNotebook(notebookId).await()
    }


}