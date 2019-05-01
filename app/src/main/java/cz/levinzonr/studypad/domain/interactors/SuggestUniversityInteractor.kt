package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.models.Location
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.rest.Api

class SuggestUniversityInteractor(private val api: Api) : BaseInputInteractor<String, University>() {
    override suspend fun executeOnBackground(input: String): University {
        return api.createUniversitySuggestionAsync(input).await()
    }
}