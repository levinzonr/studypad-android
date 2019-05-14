package cz.levinzonr.studypad.domain.repository

interface UserPreferencesRepository {

    fun setNotificationsEnabled(value: Boolean)
    fun getNotificationsEnabled() : Boolean
    fun clear()
}