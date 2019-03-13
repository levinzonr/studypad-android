package cz.levinzonr.studypad.domain.repository

import androidx.lifecycle.LiveData
import cz.levinzonr.studypad.domain.models.Note

interface NotesRepository {

    suspend fun getNotesFromNotebook(notebookId: String) : List<Note>

    suspend fun createNote(notebookId: String, title: String, content: String) : Note

    suspend fun updateNote(note: Long, title: String, content: String) : Note

    suspend fun deleteNote(noteId: Long)

    fun notesLiveData(notebookId: String) : LiveData<List<Note>>

    fun getStoredNotes(notebookId: String) : List<Note>

    fun deleteLocally(list: List<Note>)

}