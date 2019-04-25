package cz.levinzonr.studypad.domain.managers

import androidx.lifecycle.LiveData
import cz.levinzonr.studypad.domain.models.*

interface UserManager {

    fun isLoggedIn() : Boolean

    fun logout()

    fun getCurrentUserInfo() : CurrentUserInfo?

    fun getPreferences() : Preferences

    fun setNotificationsEnabled(value : Boolean)

    fun setLocale(locale: Locale)

    fun afterSuccessfulLogin(userProfile: UserProfile, token: String)
}