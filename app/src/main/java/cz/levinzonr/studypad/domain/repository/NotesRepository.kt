package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.domain.models.Note

interface NotesRepository {

    suspend fun getNotesFromNotebook(notebookId: Long) : List<Note>

}