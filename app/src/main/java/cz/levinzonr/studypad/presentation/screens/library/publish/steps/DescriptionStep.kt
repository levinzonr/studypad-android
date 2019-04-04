package cz.levinzonr.studypad.presentation.screens.library.publish.steps

import android.view.View
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.presentation.screens.library.publish.PublishModels
import ernestoyaquello.com.verticalstepperform.Step
import kotlinx.android.synthetic.main.view_publish_step_description.view.*

class DescriptionStep(stepViewClickListener: StepViewClickListener) : BaseStep<PublishModels.StepThreeResult>(stepViewClickListener,"Description") {


    override fun onStepViewCreated() {

    }

    override fun getStepResourceId(): Int = R.layout.view_publish_step_description

    override fun getStepData(): PublishModels.StepThreeResult {
        return PublishModels.StepThreeResult(stepView.notebookDescriptionEt.text.toString())
    }

}