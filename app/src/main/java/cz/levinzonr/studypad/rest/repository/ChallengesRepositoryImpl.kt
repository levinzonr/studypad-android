package cz.levinzonr.studypad.rest.repository

import androidx.lifecycle.LiveData
import cz.levinzonr.studypad.domain.models.ChallengeEntry
import cz.levinzonr.studypad.domain.repository.ChallengesRepository
import cz.levinzonr.studypad.storage.database.daos.ChallengeDao

class ChallengesRepositoryImpl(private val challengeDao: ChallengeDao) :
    ChallengesRepository {

    override fun saveChallengeEntrty(challengeEntry: ChallengeEntry) {
        challengeDao.put(challengeEntry)
    }

    override fun getRecentlyCompleted(): LiveData<List<ChallengeEntry>> {
        return challengeDao.getCompletedChallenges()
    }
}