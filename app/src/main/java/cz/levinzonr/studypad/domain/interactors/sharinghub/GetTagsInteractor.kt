package cz.levinzonr.studypad.domain.interactors.sharinghub

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor

class GetTagsInteractor : BaseInputInteractor<String, Set<String>>() {


    override suspend fun executeOnBackground(input: String): Set<String> {
        if (input.isEmpty()) return listOf("pa2", "p3", "c++", "spring", "cvut", "fit", "c", "paa", "pa1", "security").toSet()
        else {
            return setOf(input, "${input}adsa", "${input}adsads", "${input}adasdsad", "${input}ddda", "${input}azzsq")
        }
    }
}