package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.domain.models.Note

interface NotesRepository {

    suspend fun getNotesFromNotebook(notebookId: Long) : List<Note>

    suspend fun createNote(notebookId: Long, title: String, content: String) : Note

    suspend fun updateNote(note: Long, title: String, content: String) : Note

    suspend fun deleteNote(noteId: Long)

}