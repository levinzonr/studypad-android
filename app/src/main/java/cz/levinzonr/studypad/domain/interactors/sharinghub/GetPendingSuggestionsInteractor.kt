package cz.levinzonr.studypad.domain.interactors.sharinghub

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.rest.Api

class GetPendingSuggestionsInteractor(private val api: Api) : BaseInputInteractor<String, List<PublishedNotebook.Modification>>() {

    override suspend fun executeOnBackground(input: String): List<PublishedNotebook.Modification> {
        return api.getPendingSuggestionsAsync(input).await()
    }
}