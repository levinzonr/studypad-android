package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.domain.models.UserProfile
import cz.levinzonr.studypad.rest.Api

class ProfileRestRepository(private val api: Api) : ProfileRepository {

    override suspend fun getUserProfile(): UserProfile {
        return api.getAuthenticatedUserProfileAsync().await()
    }
}