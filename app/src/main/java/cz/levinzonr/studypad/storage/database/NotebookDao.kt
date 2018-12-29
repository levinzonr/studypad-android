package cz.levinzonr.studypad.storage.database

import androidx.lifecycle.LiveData
import androidx.room.*
import cz.levinzonr.studypad.domain.models.Notebook

@Dao
interface NotebookDao {

    @Query("SELECT * FROM notebook")
    fun getAll() : LiveData<List<Notebook>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(notebook: Notebook)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putAll(list: List<Notebook>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(notebook: Notebook)

    @Query("DELETE FROM notebook WHERE id = :id")
    fun delete(id: Long)

}