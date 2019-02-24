package cz.levinzonr.studypad.domain.interactors.keychain

import cz.levinzonr.studypad.domain.interactors.BaseInteractor
import cz.levinzonr.studypad.domain.managers.UserManager

class LoginInteractor(private val userManager: UserManager) : BaseInteractor<Boolean>() {

    data class Input(val email: String, val password: String)

    var input: Input? = null

    override suspend fun executeOnBackground(): Boolean {
        input?.let {
            userManager.login(it.email, it.password)
            return true
        }
        return false
    }
}