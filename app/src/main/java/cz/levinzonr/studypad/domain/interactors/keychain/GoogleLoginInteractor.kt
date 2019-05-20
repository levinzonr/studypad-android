package cz.levinzonr.studypad.domain.interactors.keychain

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.UserProfile
import cz.levinzonr.studypad.domain.repository.KeychainRepository

/**
 * Interactor to auth the user using Google
 * @param keychainRepository repository to handle the request
 */
class GoogleLoginInteractor(private val keychainRepository: KeychainRepository) : BaseInputInteractor<String, UserProfile>() {

    /**
     * Executes interactor with input
     * @param input -- Google Auth Token
     * @return profile of the user
     */
    override suspend fun executeOnBackground(input: String): UserProfile {
        return keychainRepository.loginViaGoogle(input)
    }
}