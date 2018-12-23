package cz.levinzonr.studyhub.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studyhub.domain.Note

class MockNotesRepository : NotesRepository {

    override fun getNotesFromNotebook(notebookId: Long): List<Note> {
        val list = mutableListOf<Note>()
        repeat(10) {
            list.add(Note(it.toLong(), "Note $it", "This is mocked notes conted Praesent commodo cursus magna, vel scelerisque nisl consectetur et."))
        }
        return list
    }
}