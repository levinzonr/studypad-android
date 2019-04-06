package cz.levinzonr.studypad.domain.interactors.sharinghub

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.rest.Api

class QuiclPublishInteractor(val api: Api) : BaseInputInteractor<String, PublishedNotebook.Feed>() {

    override suspend fun executeOnBackground(input: String): PublishedNotebook.Feed {
        return api.quickPublishAsync(input).await()
    }
}