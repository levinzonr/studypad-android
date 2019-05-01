package cz.levinzonr.studypad.presentation.screens.publish

import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.domain.models.University

class PublishModels {

    data class PublishViewState(
        val stepOneDefaults: StepOneData,
        val stepTwoDefaults: StepTwoData? = null,
        val stepThreeDefaults: StepThreeData? = null
    )

    data class StepOneData(val name: String, val school: University? = null, val langCode: String) : StepDataHolder {
        override fun isValid(): Boolean {
            return name.isNotBlank() && langCode.isNotEmpty()
        }

        override fun toReadableString(): String {
            return "$name ${school?.fullName ?: ""}, $langCode"
        }
    }

    data class StepTwoData(val topic: Topic?, val tags: MutableSet<String>) : StepDataHolder {
        override fun isValid(): Boolean {
            return topic != null
        }

        override fun toReadableString(): String {
            return "${topic?.name} category with ${tags.count()} tags"
        }
    }

    data class StepThreeData(val description: String) : StepDataHolder {
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

