package cz.levinzonr.studypad.domain.interactors.keychain

import cz.levinzonr.studypad.domain.interactors.BaseInteractor
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.storage.database.AppDatabase

class LogoutInteractor(private val userManager: UserManager, private val appDatabase: AppDatabase) : BaseInteractor<Unit>() {

    override suspend fun executeOnBackground() {
        userManager.logout()
        appDatabase.clearAllTables()
    }
}