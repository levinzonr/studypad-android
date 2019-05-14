package cz.levinzonr.studypad.rest.repository

import androidx.lifecycle.LiveData
import cz.levinzonr.studypad.data.CreateNoteRequest
import cz.levinzonr.studypad.data.UpdateNoteRequest
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.repository.NotesRepository
import cz.levinzonr.studypad.rest.Api
import cz.levinzonr.studypad.storage.database.AppDatabase

class NotesRepositoryImpl(
    private val remoteDataSource: Api,
    private val localDataSource: AppDatabase
) : NotesRepository {


    override suspend fun getNotesFromNotebook(notebookId: String): List<Note> {
        val notes = remoteDataSource.getNotesFromNotebookAsync(notebookId).await()
        localDataSource.notesDao().putAll(notes)
       return notes
    }

    override suspend fun createNote(notebookId: String, title: String, content: String): Note {
        val note = remoteDataSource.createNoteAsync(CreateNoteRequest(notebookId, title, content)).await()
        localDataSource.notesDao().put(note)
        return note
    }

    override suspend fun updateNote(note: Long, title: String, content: String): Note {
        val note = remoteDataSource.updateNoteAsync(note, UpdateNoteRequest(title, content)).await()
        localDataSource.notesDao().put(note)
        return note
    }

    override suspend fun deleteNote(noteId: Long) {
        remoteDataSource.deleteNoteAsync(noteId).await()
        localDataSource.notesDao().deleteById(noteId)
    }

    override fun notesLiveData(notebookId: String): LiveData<List<Note>> {
        return localDataSource.notesDao().getNotesFromNotebook(notebookId)
    }

    override fun getStoredNotes(notebookId: String): List<Note> {
        return localDataSource.notesDao().getList(notebookId)
    }

    override fun deleteLocally(list: List<Note>) {
        localDataSource.notesDao().deleteAll(list)
    }

    override fun findById(id: Long): LiveData<Note> {
        return localDataSource.notesDao().findById(id)
    }
}
