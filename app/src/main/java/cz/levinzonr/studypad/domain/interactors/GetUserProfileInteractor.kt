package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.models.UserProfile
import cz.levinzonr.studypad.domain.repository.ProfileRepository

class GetUserProfileInteractor(private val repository: ProfileRepository) : BaseInteractor<UserProfile>() {

    override suspend fun executeOnBackground(): UserProfile {
        return repository.getUserProfile()
    }
}