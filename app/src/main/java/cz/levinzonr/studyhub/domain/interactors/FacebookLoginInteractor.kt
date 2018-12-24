package cz.levinzonr.studyhub.domain.interactors

import cz.levinzonr.studyhub.domain.managers.UserManager

class FacebookLoginInteractor(private val userManager: UserManager) : BaseInteractor<Unit>() {

    data class Input(val token: String)

    var input: Input? = null

    override suspend fun executeOnBackground() {
        val token = input?.token ?: return
        userManager.loginViaFacebook(token)
    }
}