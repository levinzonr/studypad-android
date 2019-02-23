package cz.levinzonr.studypad.storage

import java.util.*

class TokenRepositoryImpl(private val prefManager: PrefManager) : TokenRepository {

    companion object {
        private const val PREF_TOKEN = "pref_token"
        private const val PREF_EXPIRES = "pref_expires_at"
    }

    override fun saveToken(token: String, expiresAt: Long) {
        prefManager.setString(PREF_TOKEN, token)
        prefManager.setLong(PREF_EXPIRES, expiresAt)
    }

    override fun getToken(): String? {
        return prefManager.getString(PREF_TOKEN, null)
    }

    override fun isValid(): Boolean {
        val expires = prefManager.getLong(PREF_EXPIRES, -1L)
        return Date().time < expires
    }

    override fun clear() {
        prefManager.remove(PREF_TOKEN)
    }
}