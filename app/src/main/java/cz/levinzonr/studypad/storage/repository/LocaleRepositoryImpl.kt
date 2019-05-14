package cz.levinzonr.studypad.storage.repository

import com.google.gson.Gson
import cz.levinzonr.studypad.domain.models.Locale
import cz.levinzonr.studypad.fromJson
import cz.levinzonr.studypad.domain.managers.PrefManager
import cz.levinzonr.studypad.domain.repository.LocaleRepository

class LocaleRepositoryImpl(private val gson: Gson, private val prefManager: PrefManager) :
    LocaleRepository {

    override suspend fun getAvailableLanguages(): List<Locale> {
        return java.util.Locale.getAvailableLocales()
            .filter { it.displayLanguage.isNotEmpty() }
            .distinctBy { it.displayLanguage }
            .sortedBy { it.displayLanguage }
            .map { Locale(it.displayName.capitalize(), it.isO3Language) }
    }

    override fun getCurrentLocale(): Locale {
        val string = prefManager.getString(PREF_LOCALE, null) ?: return getDefaultLocale()
        return gson.fromJson<Locale>(string) ?:  getDefaultLocale()
    }

    override fun saveCurrentLocale(locale: Locale) {
        prefManager.setString(PREF_LOCALE, gson.toJson(locale))
    }

    override fun getDefaultLocale(): Locale {
        val javaLocale =  java.util.Locale.getDefault()
        return Locale(javaLocale.displayName, javaLocale.isO3Language)
    }

    override fun clear() {
        prefManager.remove(PREF_LOCALE)
    }

    companion object {
        private const val PREF_LOCALE = "pref_locale"
    }
}