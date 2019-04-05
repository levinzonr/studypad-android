package cz.levinzonr.studypad.presentation.screens.library.publish.steps

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.screens.library.publish.PublishModels
import kotlinx.android.synthetic.main.view_publish_step_confirmation.view.*

class ConfirmationStep(listener: StepViewClickListener) : BaseStep<PublishModels.StepFourResult> (listener,"#4 Confirmation"){

    override fun onStepViewCreated() {
        stepView.publishButton.setOnClickListener {
            formView.completeForm()
        }
    }

    override fun getStepResourceId(): Int = R.layout.view_publish_step_confirmation

    override fun getStepData(): PublishModels.StepFourResult {
        return PublishModels.StepFourResult()
    }

}