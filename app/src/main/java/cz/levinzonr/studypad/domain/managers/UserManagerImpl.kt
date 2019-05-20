package cz.levinzonr.studypad.domain.managers

import com.google.firebase.auth.*
import com.google.firebase.messaging.FirebaseMessaging
import cz.levinzonr.studypad.domain.models.*
import cz.levinzonr.studypad.domain.repository.LocaleRepository
import cz.levinzonr.studypad.domain.repository.SearchHistoryRepository
import cz.levinzonr.studypad.domain.repository.TokenRepository
import cz.levinzonr.studypad.domain.repository.UserPreferencesRepository
import cz.levinzonr.studypad.domain.repository.UserProfileRepository


class UserManagerImpl(private val preferencesRepository: UserPreferencesRepository,
                      private val localeRepository: LocaleRepository,
                      private val tokenRepository: TokenRepository,
                      private val searchHistoryRepository: SearchHistoryRepository,
                      private val userProfileRepository: UserProfileRepository
) : UserManager {


    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun isLoggedIn(): Boolean {
        return tokenRepository.getToken() != null
    }


    override fun afterSuccessfulLogin(userProfile: UserProfile, token: String) {
        tokenRepository.saveToken(token, -1)
        userProfileRepository.saveUserProfile(userProfile)
        setNotificationsEnabled(true)
        FirebaseMessaging.getInstance().subscribeToTopic("user_${userProfile.uuid}")
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
        val user = getCurrentUserInfo()?.id
        FirebaseMessaging.getInstance().unsubscribeFromTopic("user_$user")
        firebaseAuth.signOut()
        tokenRepository.clear()
        userProfileRepository.clear()
        searchHistoryRepository.clear()
        preferencesRepository.clear()
        localeRepository.clear()

    }

}