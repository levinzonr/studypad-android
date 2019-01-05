package cz.levinzonr.studypad.storage

import cz.levinzonr.studypad.domain.models.UserProfile

interface UserProfileRepository {

    fun saveUserProfile(userProfile: UserProfile)

    fun getUserProfile() : UserProfile?

    fun clear()
}