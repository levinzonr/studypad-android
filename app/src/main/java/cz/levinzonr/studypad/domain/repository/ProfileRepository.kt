package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.domain.models.UserProfile

interface ProfileRepository {

    suspend fun getUserProfile() : UserProfile


}