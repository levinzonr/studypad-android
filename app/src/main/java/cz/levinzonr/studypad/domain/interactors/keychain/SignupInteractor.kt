package cz.levinzonr.studypad.domain.interactors.keychain

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.interactors.BaseInteractor
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.UserProfile
import cz.levinzonr.studypad.domain.repository.KeychainRepository
import java.lang.Exception

class SignupInteractor(private val repository: KeychainRepository) : BaseInputInteractor<SignupInteractor.Input, UserProfile>(){

    data class Input(val firstName: String,
                     val lastName: String,
                     val email: String,
                     val password: String)


    override suspend fun executeOnBackground(input: Input): UserProfile {
        return repository.createAccount(input.email, input.password, input.firstName, input.lastName)
    }
}