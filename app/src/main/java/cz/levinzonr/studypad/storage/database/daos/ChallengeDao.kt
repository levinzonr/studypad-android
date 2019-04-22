package cz.levinzonr.studypad.storage.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.levinzonr.studypad.domain.models.ChallengeEntry

@Dao
interface ChallengeDao {


    @Query("SELECT * from ChallengeEntry")
    fun getCompletedChallenges() : LiveData<List<ChallengeEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(challengeEntry: ChallengeEntry)
}