package cz.levinzonr.studypad.storage.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.levinzonr.studypad.domain.models.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM note WHERE notebookId = :notebookId")
    fun getNotesFromNotebook(notebookId: Long) : LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putAll(list: List<Note>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(note: Note)

}
