package cz.levinzonr.studypad.presentation.screens.library.publish.steps

import android.view.View
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.presentation.screens.library.publish.PublishModels
import ernestoyaquello.com.verticalstepperform.Step

class DescriptionStep : BaseStep<PublishModels.StepThreeResult>("Description") {



    override fun isStepDataValid(stepData: PublishModels.StepThreeResult?): IsDataValid {
        return IsDataValid(true)
    }




    override fun createStepContentLayout(): View {
        val view =  context.layoutInflater.inflate(R.layout.view_publish_step_description, null, false)

        return view
    }

    override fun getStepData(): PublishModels.StepThreeResult {
        return PublishModels.StepThreeResult("")
    }

}