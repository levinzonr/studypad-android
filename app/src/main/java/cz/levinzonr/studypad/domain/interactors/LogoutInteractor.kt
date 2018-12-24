package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.managers.UserManager

class LogoutInteractor(private val userManager: UserManager) : BaseInteractor<Unit>() {

    override suspend fun executeOnBackground() {
        userManager.logout()
    }
}