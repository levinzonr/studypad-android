package cz.levinzonr.studypad.presentation.screens.publish.steps

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.screens.publish.PublishModels
import kotlinx.android.synthetic.main.view_publish_step_confirmation.view.*

class ConfirmationStep(stepViewClickListener: StepViewClickListener, title: String, content: String) : BaseStep<PublishModels.StepFourResult>(stepViewClickListener,title, content) {

    override fun onStepViewCreated() {
        stepView.publishButton.setOnClickListener {
            formView.completeForm()
        }
    }

    override fun getStepResourceId(): Int = R.layout.view_publish_step_confirmation

    override fun getStepData(): PublishModels.StepFourResult {
        return PublishModels.StepFourResult()
    }

    override fun setDefaultData(stepData: PublishModels.StepFourResult) {

    }
}