package cz.levinzonr.studypad.presentation.screens.library.publish

import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.domain.models.University

class PublishModels {


    data class StepOneResult(val name: String, val school: University? = null, val langCode: String) : StepDataHolder {
        override fun isValid(): Boolean {
            return name.isNotBlank() && langCode.isNotEmpty()
        }

        override fun toReadableString(): String {
            return "$name ${school?.fullName ?: ""}, $langCode"
        }
    }

    data class StepTwoResult(val topic: Topic?, val tags: MutableSet<String>) : StepDataHolder {
        override fun isValid(): Boolean {
            return topic != null
        }

        override fun toReadableString(): String {
            return "${topic?.name} category with ${tags.count()} tags"
        }
    }

    data class StepThreeResult(val description: String) : StepDataHolder {
        override fun isValid(): Boolean {
            return true
        }

        override fun toReadableString(): String {
            return description
        }
    }

    class StepFourResult() : StepDataHolder {
        override fun isValid(): Boolean {
            return true
        }

        override fun toReadableString(): String {
            return ""
        }
    }

}

interface StepDataHolder {
    fun isValid() : Boolean
    fun toReadableString() : String
}

