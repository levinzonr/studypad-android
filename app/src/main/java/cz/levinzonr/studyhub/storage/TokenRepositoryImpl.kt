package cz.levinzonr.studyhub.storage

class TokenRepositoryImpl(private val prefManager: PrefManager) : TokenRepository {

    companion object {
        private const val PREF_TOKEN = "pref_token"
    }

    override fun saveToken(token: String) {
        prefManager.setString(PREF_TOKEN, token)
    }

    override fun getToken(): String? {
        return prefManager.getString(PREF_TOKEN, null)
    }
}