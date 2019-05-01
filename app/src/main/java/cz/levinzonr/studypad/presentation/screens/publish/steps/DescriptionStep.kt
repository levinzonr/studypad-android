package cz.levinzonr.studypad.presentation.screens.publish.steps

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.screens.publish.PublishModels
import kotlinx.android.synthetic.main.view_publish_step_description.view.*

class DescriptionStep(stepViewClickListener: StepViewClickListener, title: String, content: String) : BaseStep<PublishModels.StepThreeData>(stepViewClickListener,title, content) {


    override fun onStepViewCreated() {
        stepView.nextStepBtn.setOnClickListener { formView.goToNextStep(true) }
    }

    override fun getStepResourceId(): Int = R.layout.view_publish_step_description

    override fun getStepData(): PublishModels.StepThreeData {
        return PublishModels.StepThreeData(stepView.notebookDescriptionEt.text.toString())
    }


    override fun setDefaultData(stepData: PublishModels.StepThreeData) {
        stepView.notebookDescriptionEt.setText(stepData.description)
    }
}