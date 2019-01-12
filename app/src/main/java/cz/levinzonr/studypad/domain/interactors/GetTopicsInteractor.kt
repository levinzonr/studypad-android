package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.rest.Api

class GetTopicsInteractor(private val api: Api) : BaseInputInteractor<String, List<Topic>>() {

    override suspend fun executeOnBackground(input: String): List<Topic> {
        return List(20, init = {i -> Topic(i.toLong(), "Topic $i") })
    }
}