package cz.levinzonr.studypad.presentation.screens.library.publish.steps

import com.google.android.material.chip.Chip
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.presentation.screens.library.publish.PublishModels
import cz.levinzonr.studypad.removeIf
import cz.levinzonr.studypad.views
import kotlinx.android.synthetic.main.view_publish_step_info.view.*

class AdditionalInfoStep(listener: StepViewClickListener) : BaseStep<PublishModels.StepTwoResult>(listener,"#2 Notebook's theme", "Help other users to find your notebook by sharing some additional details") {



    override fun getStepResourceId(): Int = R.layout.view_publish_step_info

    override fun onStepViewCreated() {
        updateButton()
        stepView.notebookTopicEt.setOnClickListener { listener?.onClick(it) }
        val addTagButton = Chip(context).apply {
            text = "Add Tag"
            setChipIconResource(R.drawable.ic_round_add_24px)
            setChipIconTintResource(R.color.blue)
        }
        addTagButton.setOnClickListener { listener?.onClick(it) }
        stepView.notebookTagsCG.addView(addTagButton)
        stepView.nextStepBtn.setOnClickListener { formView.goToNextStep(true) }
    }


    override fun getStepData(): PublishModels.StepTwoResult {
        val tags = stepView.notebookTagsCG.views
            .filter { (it as Chip).chipIcon == null }
            .map { (it as Chip).text.toString() }

        return PublishModels.StepTwoResult(stepView.notebookTopicEt.tag as? Topic?, tags.toMutableSet())
    }

    fun setTopic(topic: Topic) {
        stepView.notebookTopicEt.setText(topic.name)
        stepView.notebookTopicEt.tag = topic
        markAsCompletedOrUncompleted(true)
        updateButton()
    }


    fun toggleTag(tag: String, enable: Boolean) {
        if (!enable) {
            stepView.notebookTagsCG.removeIf { (it as Chip).text == tag }
        } else {
            stepView.notebookTagsCG.addView(Chip(context).apply { text = tag })

        }
        markAsCompletedOrUncompleted(true)
        updateButton()
    }

    private fun updateButton() {
        stepView.nextStepBtn.isEnabled = isStepDataValid
    }

}