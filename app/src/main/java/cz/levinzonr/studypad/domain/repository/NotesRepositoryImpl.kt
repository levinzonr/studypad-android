package cz.levinzonr.studypad.domain.repository

import androidx.lifecycle.LiveData
import cz.levinzonr.studypad.data.CreateNoteRequest
import cz.levinzonr.studypad.data.UpdateNoteRequest
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.rest.Api
import cz.levinzonr.studypad.storage.database.AppDatabase

class NotesRepositoryImpl(
    private val remoteDataSource: Api,
    private val localDataSource: AppDatabase
) : NotesRepository {


    override suspend fun getNotesFromNotebook(notebookId: Long): List<Note> {
        val notes = remoteDataSource.getNotesFromNotebook(notebookId).await().map { it.copy(notebookId = notebookId) }
        localDataSource.notesDao().putAll(notes)
       return notes
    }

    override suspend fun createNote(notebookId: Long, title: String, content: String): Note {
        val note = remoteDataSource.createNote(CreateNoteRequest(notebookId, title, content)).await()
        localDataSource.notesDao().put(note.copy(notebookId = notebookId))
        return note
    }

    override suspend fun updateNote(note: Long, title: String, content: String): Note {
        val note = remoteDataSource.updateNote(note, UpdateNoteRequest(title, content)).await()
        localDataSource.notesDao().put(note)
        return note
    }

    override suspend fun deleteNote(noteId: Long) {
        remoteDataSource.deleteNote(noteId).await()
    }

    override fun notesLiveData(notebookId: Long): LiveData<List<Note>> {
        return localDataSource.notesDao().getNotesFromNotebook(notebookId)
    }
}
