package cz.levinzonr.studypad.presentation.screens.library.publish

import cz.levinzonr.studypad.domain.models.Locale
import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.domain.models.University

class PublishModels {


    data class StepOneResult(val name: String, val school: University? = null, val langCode: String) : Validatable {
        override fun isValid(): Boolean {
            return name.isNotBlank() && langCode.isNotEmpty()
        }
    }

    data class StepTwoResult(val topic: Topic?, val tags: MutableSet<String>) : Validatable {
        override fun isValid(): Boolean {
            return topic != null
        }
    }

    data class StepThreeResult(val description: String) : Validatable {
        override fun isValid(): Boolean {
            return true
        }
    }

}

interface Validatable {
    fun isValid() : Boolean
}

