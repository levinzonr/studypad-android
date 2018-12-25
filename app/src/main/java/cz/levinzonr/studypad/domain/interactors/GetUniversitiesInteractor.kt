package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.rest.Api

class GetUniversitiesInteractor(val api: Api) : BaseInteractor<List<University>>(){

    data class Input(val query: String)

    var input: Input? = null


    override suspend fun executeOnBackground(): List<University> {
        return api.getUniversities(input?.query ?: "").await()
    }
}