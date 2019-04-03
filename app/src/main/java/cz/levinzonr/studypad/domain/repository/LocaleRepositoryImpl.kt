package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.domain.models.Locale

class LocaleRepositoryImpl : LocaleRepository {

    override suspend fun getAvailableLanguages(): List<Locale> {
        return java.util.Locale.getAvailableLocales()
            .filter { it.displayLanguage.isNotEmpty() }
            .distinctBy { it.displayLanguage }
            .sortedBy { it.displayLanguage }
            .map { Locale(it.displayName, it.isO3Language) }
    }
}