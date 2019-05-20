package cz.levinzonr.studypad.domain.interactors.keychain

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.UserProfile
import cz.levinzonr.studypad.domain.repository.KeychainRepository

/**
 * Interactor to authenticate user using facebook
 * @param keychainRepository -- repository to handle the request
 */
class FacebookLoginInteractor(private val keychainRepository: KeychainRepository) : BaseInputInteractor<String, UserProfile>() {


    /**
     * Executes the interactor with input
     * @param input -- facebook token
     * @return user's profile
     */
    override suspend fun executeOnBackground(input: String) : UserProfile {
        return keychainRepository.loginViaFacebook(input)
    }
}