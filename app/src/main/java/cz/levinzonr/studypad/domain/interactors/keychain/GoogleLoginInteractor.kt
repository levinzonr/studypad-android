package cz.levinzonr.studypad.domain.interactors.keychain

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.UserProfile
import cz.levinzonr.studypad.domain.repository.KeychainRepository

class GoogleLoginInteractor(private val keychainRepository: KeychainRepository) : BaseInputInteractor<String, UserProfile>() {

    override suspend fun executeOnBackground(input: String): UserProfile {
        return keychainRepository.loginViaGoogle(input)
    }
}