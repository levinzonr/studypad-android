package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.domain.Note

interface NotesRepository {

    suspend fun getNotesFromNotebook(notebookId: Long) : List<Note>

}