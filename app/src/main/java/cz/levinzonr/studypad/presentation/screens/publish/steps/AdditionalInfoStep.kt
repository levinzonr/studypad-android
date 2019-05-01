package cz.levinzonr.studypad.presentation.screens.publish.steps

import com.google.android.material.chip.Chip
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.presentation.screens.publish.PublishModels
import cz.levinzonr.studypad.removeIf
import cz.levinzonr.studypad.views
import kotlinx.android.synthetic.main.view_publish_step_info.view.*

class AdditionalInfoStep(listener: StepViewClickListener, title: String, content: String) : BaseStep<PublishModels.StepTwoData>(listener,title, content) {



    override fun getStepResourceId(): Int = R.layout.view_publish_step_info

    override fun onStepViewCreated() {
        updateButton()
        stepView.notebookTopicEt.setOnClickListener { listener?.onClick(it) }
        val addTagButton = Chip(context).apply {
            text = context.getString(R.string.publish_step2_tags_add)
            setChipIconResource(R.drawable.ic_round_add_24px)
            setChipIconTintResource(R.color.blue)
        }
        addTagButton.setOnClickListener { listener?.onClick(it) }
        stepView.notebookTagsCG.addView(addTagButton)
        stepView.nextStepBtn.setOnClickListener { formView.goToNextStep(true) }
    }


    override fun getStepData(): PublishModels.StepTwoData {
        val tags = stepView.notebookTagsCG.views
            .filter { (it as Chip).chipIcon == null }
            .map { (it as Chip).text.toString() }

        return PublishModels.StepTwoData(stepView.notebookTopicEt.tag as? Topic?, tags.toMutableSet())
    }

    fun setTopic(topic: Topic) {
        stepView.notebookTopicEt.setText(topic.name)
        stepView.notebookTopicEt.tag = topic
        markAsCompletedOrUncompleted(true)
        updateButton()
    }

    override fun setDefaultData(stepData: PublishModels.StepTwoData) {
        stepData.tags.forEach { toggleTag(it, true )}
    }

    fun toggleTag(tag: String, enable: Boolean) {
        if (!enable) {
            stepView.notebookTagsCG.removeIf { (it as Chip).text == tag }
        } else {
            val chip =  Chip(context)
            chip.isCloseIconVisible = true
            chip.text = tag
            chip.setOnCloseIconClickListener {
                toggleTag(tag, false)
            }
            stepView.notebookTagsCG.addView(chip)
        }
        markAsCompletedOrUncompleted(true)
        updateButton()
    }

    private fun updateButton() {
        stepView.nextStepBtn.isEnabled = isStepDataValid
    }

}