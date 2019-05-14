package cz.levinzonr.studypad.rest.repository

import cz.levinzonr.studypad.domain.repository.FirebaseTokenRepository
import cz.levinzonr.studypad.domain.managers.PrefManager

class FirebaseTokenRepositoryImpl(private val prefManager: PrefManager) :
    FirebaseTokenRepository {


    override fun putToken(token: String) {
        prefManager.setString(PREF_TOKEN, token)
        prefManager.setBoolean(PREF_IS_REFRESHED, false)
    }

    override fun isTokenRefreshed(): Boolean {
        return prefManager.getBoolean(PREF_IS_REFRESHED, false)
    }

    override fun getToken(): String? {
        return prefManager.getString(PREF_TOKEN, null)
    }

    override fun markAsRefreshed() {
        prefManager.setBoolean(PREF_IS_REFRESHED, true)
    }

    companion object {
        private const val PREF_TOKEN = "token"
        private const val PREF_IS_REFRESHED = "token_refresh"
    }
}