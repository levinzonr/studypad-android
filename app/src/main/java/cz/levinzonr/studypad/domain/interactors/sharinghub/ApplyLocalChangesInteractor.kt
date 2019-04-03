package cz.levinzonr.studypad.domain.interactors.sharinghub

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.rest.Api

class ApplyLocalChangesInteractor(private val api: Api) : BaseInputInteractor<String, PublishedNotebook.Detail>() {

    override suspend fun executeOnBackground(input: String): PublishedNotebook.Detail {
        return api.applyLocalChangesAsync(input).await()
    }
}