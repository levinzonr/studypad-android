package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.domain.models.UserProfile

interface UserProfileRepository {

    fun saveUserProfile(userProfile: UserProfile)

    fun getUserProfile() : UserProfile?

    fun clear()
}