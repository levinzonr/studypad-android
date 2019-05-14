package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.models.UserProfile
import cz.levinzonr.studypad.domain.repository.ProfileRepository
import cz.levinzonr.studypad.domain.repository.UserProfileRepository

class GetUserProfileInteractor(private val repository: ProfileRepository, private val userProfileRepository: UserProfileRepository) : BaseInteractor<UserProfile>() {

    override suspend fun executeOnBackground(): UserProfile {
        return repository.getUserProfile().also {
            userProfileRepository.saveUserProfile(it)
        }
    }
}