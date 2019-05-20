package cz.levinzonr.studypad.domain.interactors.keychain

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.interactors.BaseInteractor
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.repository.KeychainRepository

/**
 * Interactor to login the user using email and password
 * @param keychainRepository to handle the request
 */
class LoginInteractor(private val keychainRepository: KeychainRepository) : BaseInputInteractor<LoginInteractor.Input, Unit>() {

    /**
     * Interactors input
     * @param email -- user's email
     * @param password -- user's password
     */
    data class Input(val email: String, val password: String)

    override suspend fun executeOnBackground(input: Input) {
        return keychainRepository.login(input.email, input.password)
    }
}