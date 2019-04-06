package cz.levinzonr.studypad.domain.interactors.sharinghub

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.rest.Api

class GetTopicsInteractor(private val api: Api) : BaseInputInteractor<String, List<Topic>>() {

    override suspend fun executeOnBackground(input: String): List<Topic> {
        return api.getTopicsAsync().await()
    }
}