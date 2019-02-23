package cz.levinzonr.studypad.domain.interactors.keychain

import cz.levinzonr.studypad.domain.interactors.BaseInteractor
import cz.levinzonr.studypad.domain.managers.UserManager

class SignupInteractor(private val userManager: UserManager) : BaseInteractor<String>(){

    data class Input(val firstName: String,
                     val lastName: String,
                     val email: String,
                     val password: String)


    var input: Input? = null

    override suspend fun executeOnBackground() : String {
        input?.let {
           return userManager.createAccount(it.email, it.password, it.firstName, it.lastName)
        }
    }
}