package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.models.Locale
import cz.levinzonr.studypad.domain.repository.LocaleRepository

class GetAvailableLanguagesInteractor(private val localeRepository: LocaleRepository): BaseInteractor<List<Locale>>() {

    override suspend fun executeOnBackground(): List<Locale> {
        return localeRepository.getAvailableLanguages()
    }
}