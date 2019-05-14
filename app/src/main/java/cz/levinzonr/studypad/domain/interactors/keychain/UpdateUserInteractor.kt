package cz.levinzonr.studypad.domain.interactors.keychain

import cz.levinzonr.studypad.data.UpdateProfileRequest
import cz.levinzonr.studypad.domain.interactors.BaseInteractor
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.rest.Api
import cz.levinzonr.studypad.domain.repository.UserProfileRepository

class UpdateUserInteractor(private val api: Api, private val userProfileRepository: UserProfileRepository) : BaseInteractor<Unit>() {

    data class Input(val uni: University?, val displayName: String? = null)

    var input: Input? = null

    override suspend fun executeOnBackground() {
        val request = UpdateProfileRequest(input?.uni?.id, input?.displayName)
        val user =  api.updateUserAsync(request).await()
        userProfileRepository.saveUserProfile(user)
    }
}