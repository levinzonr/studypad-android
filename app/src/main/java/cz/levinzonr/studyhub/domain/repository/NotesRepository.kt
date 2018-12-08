package cz.levinzonr.studyhub.domain.repository

import androidx.lifecycle.LiveData
import cz.levinzonr.studyhub.domain.Note

interface NotesRepository {

    fun getNotesFromNotebook(notebookId: Long) : LiveData<List<Note>>

}