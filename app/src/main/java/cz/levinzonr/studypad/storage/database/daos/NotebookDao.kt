package cz.levinzonr.studypad.storage.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.models.Notebook

@Dao
interface NotebookDao {

    @Query("SELECT * FROM notebook")
    fun getAllLiveData() : LiveData<List<Notebook>>

    @Query("SELECT * FROM notebook")
    fun getAll() : List<Notebook>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(notebook: Notebook)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putAll(list: List<Notebook>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(notebook: Notebook)

    @Query("DELETE FROM notebook WHERE id = :id")
    fun delete(id: String)

}