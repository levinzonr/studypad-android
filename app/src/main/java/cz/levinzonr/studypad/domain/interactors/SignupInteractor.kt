package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.managers.UserManager

class SignupInteractor(private val userManager: UserManager) : BaseInteractor<Unit>(){

    data class Input(val firstName: String,
                     val lastName: String,
                     val email: String,
                     val password: String)


    var input: Input? = null

    override suspend fun executeOnBackground() {
        input?.let {
            userManager.createAccount(it.email, it.password, it.firstName, it.lastName)
        }
    }
}