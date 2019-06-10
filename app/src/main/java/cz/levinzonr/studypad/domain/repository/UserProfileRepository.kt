package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.domain.models.UserProfile

/**
 * Repository to store user profile information
 */
interface UserProfileRepository {

    /**
     * Saves user profile
     * @param userProfile - user profile to save
     */
    fun saveUserProfile(userProfile: UserProfile)

    /**
     * Get saved user profile
     * @return saved user profile if any, null otherwise
     */
    fun getUserProfile() : UserProfile?

    /**
     * Clears repository removing user info
     */
    fun clear()
}