package cz.levinzonr.studyhub.domain.repository

import androidx.lifecycle.LiveData
import cz.levinzonr.studyhub.domain.Note

interface NotesRepository {

    suspend fun getNotesFromNotebook(notebookId: Long) : List<Note>

}