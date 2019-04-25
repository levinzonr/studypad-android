package cz.levinzonr.studypad.domain.managers

import androidx.lifecycle.LiveData
import com.google.firebase.auth.*
import cz.levinzonr.studypad.data.CreateAccountRequest
import cz.levinzonr.studypad.domain.models.*
import cz.levinzonr.studypad.domain.repository.LocaleRepository
import cz.levinzonr.studypad.domain.repository.SearchHistoryRepository
import cz.levinzonr.studypad.rest.Api
import cz.levinzonr.studypad.storage.TokenRepository
import cz.levinzonr.studypad.storage.UserPreferencesRepository
import cz.levinzonr.studypad.storage.UserProfileRepository
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserManagerImpl(private val preferencesRepository: UserPreferencesRepository,
                      private val localeRepository: LocaleRepository,
                      private val tokenRepository: TokenRepository,
                      private val searchHistoryRepository: SearchHistoryRepository,
                      private val userProfileRepository: UserProfileRepository) : UserManager {


    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun isLoggedIn(): Boolean {
        return tokenRepository.getToken() != null
    }


    override fun afterSuccessfulLogin(userProfile: UserProfile, token: String) {
        tokenRepository.saveToken(token, -1)
        userProfileRepository.saveUserProfile(userProfile)
        setNotificationsEnabled(true)
    }

    override fun getCurrentUserInfo(): CurrentUserInfo? {
        val userInfo = userProfileRepository.getUserProfile() ?: return null
        val locale = localeRepository.getCurrentLocale()
        return CurrentUserInfo(userInfo.uuid, userInfo.displayName, userInfo.photoUrl, userInfo.university, locale)
    }


    override fun getPreferences(): Preferences {
        val notificatios = preferencesRepository.getNotificationsEnabled()
        return Preferences(notificatios)
    }

    override fun setNotificationsEnabled(value: Boolean) {
        preferencesRepository.setNotificationsEnabled(value)
    }

    override fun setLocale(locale: Locale) {
        localeRepository.saveCurrentLocale(locale)
    }

    override fun logout() {
        firebaseAuth.signOut()
        tokenRepository.clear()
        userProfileRepository.clear()
        searchHistoryRepository.clear()
        preferencesRepository.clear()
        localeRepository.clear()
    }

}