package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.domain.models.Locale

/**
 * Repository to act on the Locale Entity
 */
interface LocaleRepository {

    /**
     * Returns the list of available languages
     */
    suspend fun getAvailableLanguages() : List<Locale>

    /**
     * Returns the currently activated locale
     */
    fun getCurrentLocale() : Locale


    fun getDefaultLocale() : Locale


    /**
     * Activates a new locale
     * @param locale to be set as a new one
      */
    fun saveCurrentLocale(locale: Locale)


    /**
     * Clear all the previosly saved preferences
     */
    fun clear()

}