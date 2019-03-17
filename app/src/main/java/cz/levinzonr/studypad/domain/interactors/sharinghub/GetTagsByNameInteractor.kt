package cz.levinzonr.studypad.domain.interactors.sharinghub

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.rest.Api

class GetTagsByNameInteractor(val api: Api) : BaseInputInteractor<String, Set<String>>() {


    override suspend fun executeOnBackground(input: String): Set<String> {
       val result =  api.findTagsByName(input).await().toMutableList()
        return if (!result.contains(input) && input.isNotBlank()) {
            result.apply { add(0, input) }.toSet()
        } else {
            result.toSet()
        }
    }
}