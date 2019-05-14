package cz.levinzonr.studypad.domain.managers

import androidx.lifecycle.LiveData
import cz.levinzonr.studypad.domain.models.*


/**
 * An interface that contains helper methods related to current user session
 */
interface UserManager {


    /**
     * Determines it there is an active user session
     * @return true if there is an active user session
     */
    fun isLoggedIn() : Boolean

    /**
     * Method that allows to finish current user session, thus loggin them out of the application
     */
    fun logout()


    /**
     * Allows to access basic information about current user
     * @return a data class with different user properties
     * @see CurrentUserInfo
     */
    fun getCurrentUserInfo() : CurrentUserInfo?

    /**
     * Allows to access current user preferences
     * @return cuurent user preferences
     * @see Preferences
     */
    fun getPreferences() : Preferences

    /**
     * Sets a new value for notifications preference
     * @param value is new value to be set
     */
    fun setNotificationsEnabled(value : Boolean)

    /**
     * Allow to set new locale for the current user, as apposed to default one
     * @param locale is the new user locale to be set
     */
    fun setLocale(locale: Locale)

    /**
     * Convenience method to handle the user that has successfully started his session
     * @param userProfile contain various user properties
     * @param token is the API access token that is used fire a request on users behalf
     */
    fun afterSuccessfulLogin(userProfile: UserProfile, token: String)
}