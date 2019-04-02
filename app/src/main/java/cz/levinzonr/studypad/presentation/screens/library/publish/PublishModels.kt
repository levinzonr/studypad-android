package cz.levinzonr.studypad.presentation.screens.library.publish

import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.domain.models.University

class PublishModels {


    data class StepOneResult(val name: String, val schoolId: University? = null, val langCode: String)

    data class StepTwoResult(val topic: Topic?, val tags: MutableSet<String>)

    data class StepThreeResult(val description: String)

}