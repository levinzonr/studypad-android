package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.data.UpdateUniversityRequest
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.rest.Api

class UpdateUserUniversityInteractor(private val api: Api) : BaseInteractor<Any>() {

    data class Input(val uni: University)

    var input: Input? = null

    override suspend fun executeOnBackground(): Any {
        val id = input?.uni?.id ?: -1
        return api.updateUniversity(UpdateUniversityRequest(id))
    }
}