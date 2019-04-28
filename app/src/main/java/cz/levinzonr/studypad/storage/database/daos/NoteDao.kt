package cz.levinzonr.studypad.storage.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete

import cz.levinzonr.studypad.domain.models.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM note WHERE notebookId = :notebookId")
    fun getNotesFromNotebook(notebookId: String) : LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putAll(list: List<Note>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(note: Note)

    @Query("SELECT * FROM note WHERE notebookId = :notebookId")
    fun getList(notebookId: String) : List<Note>

    @Query("SELECT * FROM note WHERE id = :id")
    fun findById(id: Long) : LiveData<Note>

    @Delete
    fun deleteAll(list: List<Note>)

    @Query("DELETE FROM Note where id = :id")
    fun deleteById(id: Long)

}
