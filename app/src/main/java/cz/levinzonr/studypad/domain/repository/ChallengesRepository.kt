package cz.levinzonr.studypad.domain.repository

import androidx.lifecycle.LiveData
import cz.levinzonr.studypad.domain.models.ChallengeEntry

/**
 * Challenges repository to store @see ChallengeEntry
 */
interface ChallengesRepository {


    /**
     * Saves newest challenge entrt
     */
    fun saveChallengeEntrty(challengeEntry: ChallengeEntry)

    /**
     * Get Recently completed challenges as live data
     */
    fun getRecentlyCompleted() : LiveData<List<ChallengeEntry>>
}