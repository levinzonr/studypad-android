package cz.levinzonr.studypad.storage.repository

import cz.levinzonr.studypad.domain.managers.PrefManager
import cz.levinzonr.studypad.domain.repository.UserPreferencesRepository

class UserPreferencesRepositoryImpl(private val prefManager: PrefManager) :
    UserPreferencesRepository {


    override fun setNotificationsEnabled(value: Boolean) {
        prefManager.setBoolean(PREF_NOTIFICATIONS, value)
    }

    override fun getNotificationsEnabled(): Boolean {
        return prefManager.getBoolean(PREF_NOTIFICATIONS, false)
    }

    override fun clear() {
        prefManager.remove(PREF_NOTIFICATIONS)
    }

    companion object {
        private const val PREF_NOTIFICATIONS = "pref_notifications"
    }
}