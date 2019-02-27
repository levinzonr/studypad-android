package cz.levinzonr.studypad.domain.interactors.sharinghub

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.rest.Api

class QuiclPublishInteractor(val api: Api) : BaseInputInteractor<Long, PublishedNotebook.Feed>() {

    override suspend fun executeOnBackground(input: Long): PublishedNotebook.Feed {
        return api.quickPublish(input).await()
    }
}