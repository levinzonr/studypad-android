package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.domain.models.Locale

interface LocaleRepository {

    suspend fun getAvailableLanguages() : List<Locale>

    fun getCurrentLocale() : Locale

    fun getDefaultLocale() : Locale

    fun saveCurrentLocale(locale: Locale)

}