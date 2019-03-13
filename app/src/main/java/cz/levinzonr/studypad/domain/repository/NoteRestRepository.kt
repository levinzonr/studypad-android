/*
package cz.levinzonr.studypad.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.data.CreateNoteRequest
import cz.levinzonr.studypad.data.UpdateNoteRequest
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.rest.Api

class NoteRestRepository(private val api: Api) : NotesRepository {

    override suspend fun getNotesFromNotebook(notebookId: Long): List<Note> {
        return api.getNotesFromNotebook(notebookId).await()
    }


    override suspend fun createNote(notebookId: Long, title: String, content: String): Note {
        val request = CreateNoteRequest(notebookId, title, content)
        return api.createNote(request).await()
    }

    override suspend fun updateNote(note: Long, title: String, content: String): Note {
        val request = UpdateNoteRequest(title, content)
        return api.updateNote(note, request).await()
    }

    override suspend fun deleteNote(noteId: Long) {
        return api.deleteNote(noteId).await()
    }

    override fun notesLiveData(notebookId: Long): LiveData<List<Note>> {
        return MutableLiveData()
    }

    override fun getStoredNotes(notebookId: Long): List<Note> {
        return listOf()
    }

    override fun deleteLocally(list: List<Note>) {}
}*/
