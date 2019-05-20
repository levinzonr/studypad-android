package cz.levinzonr.studypad.domain.interactors.keychain

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.interactors.BaseInteractor
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.UserProfile
import cz.levinzonr.studypad.domain.repository.KeychainRepository
import java.lang.Exception

/**
 * Interactor that creates a new User
 */
class SignupInteractor(private val repository: KeychainRepository) : BaseInputInteractor<SignupInteractor.Input, UserProfile>(){

    data class Input(val firstName: String,
                     val lastName: String,
                     val email: String,
                     val password: String)


    /**
     * Executes an interactor with an input
     * @param input -- interactors input with the users details
     * @return the newly created user's profiel
     */
    override suspend fun executeOnBackground(input: Input): UserProfile {
        return repository.createAccount(input.email, input.password, input.firstName, input.lastName)
    }
}