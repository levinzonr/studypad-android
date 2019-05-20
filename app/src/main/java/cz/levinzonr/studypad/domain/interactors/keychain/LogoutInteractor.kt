package cz.levinzonr.studypad.domain.interactors.keychain

import cz.levinzonr.studypad.domain.interactors.BaseInteractor
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.rest.Api
import cz.levinzonr.studypad.storage.database.AppDatabase

/**
 * Interactor to logout the user
 * @param userManager -- user manager to handle session clearing
 * @param appDatabase -- app database
 */
class LogoutInteractor(private val userManager: UserManager, private val appDatabase: AppDatabase) : BaseInteractor<Unit>() {

    override suspend fun executeOnBackground() {
        userManager.logout()
        appDatabase.clearAllTables()
    }
}