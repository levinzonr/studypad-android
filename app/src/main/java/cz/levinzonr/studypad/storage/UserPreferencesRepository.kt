package cz.levinzonr.studypad.storage

interface UserPreferencesRepository {

    fun setNotificationsEnabled(value: Boolean)
    fun getNotificationsEnabled() : Boolean
    fun clear()
}