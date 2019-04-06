package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.rest.Api

class GetUniversitiesInteractor(val api: Api) : BaseInputInteractor<String, List<University>>(){



    override suspend fun executeOnBackground(input: String): List<University> {
        return api.getUniversitiesAsync(input).await()
    }
}