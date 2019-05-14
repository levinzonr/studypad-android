package cz.levinzonr.studypad.storage.repository

import cz.levinzonr.studypad.domain.managers.PrefManager
import cz.levinzonr.studypad.domain.repository.TokenRepository
import timber.log.Timber
import java.util.*

class TokenRepositoryImpl(private val prefManager: PrefManager) :
    TokenRepository {

    companion object {
        private const val PREF_TOKEN = "pref_token"
        private const val PREF_EXPIRES = "pref_expires_at"
    }

    override fun saveToken(token: String, expiresAt: Long) {
        Timber.d("save token ${Date(expiresAt)}")
        prefManager.setString(PREF_TOKEN, token)
        prefManager.setLong(PREF_EXPIRES, expiresAt)
    }

    override fun getToken(): String? {
        Timber.d("Expires at: ${Date(prefManager.getLong(PREF_EXPIRES, -1L) * 1000)}")
        return prefManager.getString(PREF_TOKEN, null)
    }

    override fun isValid(): Boolean {
        val expires = prefManager.getLong(PREF_EXPIRES, -1L)
        return Date().time < expires
    }

    override fun clear() {
        prefManager.remove(PREF_TOKEN)
        prefManager.remove(PREF_EXPIRES)
    }
}