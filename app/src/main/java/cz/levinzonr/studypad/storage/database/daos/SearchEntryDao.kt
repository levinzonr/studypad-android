package cz.levinzonr.studypad.storage.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.levinzonr.studypad.domain.models.SearchEntry

@Dao
interface SearchEntryDao {

    @Query("SELECT * FROM searchentry ORDER By lastlyUsed DESC")
    fun getAll() : LiveData<List<SearchEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(searchEntry: SearchEntry)

    @Query("DELETE FROM searchentry")
    fun deleteAll()
}