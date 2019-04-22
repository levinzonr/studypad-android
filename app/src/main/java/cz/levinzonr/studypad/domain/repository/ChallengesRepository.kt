package cz.levinzonr.studypad.domain.repository

import androidx.lifecycle.LiveData
import cz.levinzonr.studypad.domain.models.ChallengeEntry

interface ChallengesRepository {


    fun saveChallengeEntrty(challengeEntry: ChallengeEntry)

    fun getRecentlyCompleted() : LiveData<List<ChallengeEntry>>
}