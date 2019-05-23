package cz.levinzonr.studypad.storage.repository

import com.google.gson.Gson
import cz.levinzonr.studypad.domain.managers.PrefManager
import cz.levinzonr.studypad.domain.models.UserProfile
import cz.levinzonr.studypad.domain.repository.UserProfileRepository

/**
 * UserProfile repository implementation
 * @see UserProfileRepository
 * @param gson - Gson instance to serilialze object
 * @param prefManager - preferences manager
 */
class UserProfileRepositoryImpl(val gson: Gson, private val prefManager: PrefManager) :
    UserProfileRepository {

    companion object {
        private const val PREF_USER = "pref_user_info"
    }

    override fun saveUserProfile(userProfile: UserProfile) {
        val string = gson.toJson(userProfile)
        prefManager.setString(PREF_USER, string)
    }

    override fun getUserProfile(): UserProfile? {
        val string = prefManager.getString(PREF_USER, null) ?: return null
        return gson.fromJson(string, UserProfile::class.java)
    }

    override fun clear() {
        prefManager.remove(PREF_USER)
    }
}