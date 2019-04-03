package cz.levinzonr.studypad.presentation.screens.library.publish.steps

import android.view.View
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.presentation.screens.library.publish.PublishModels
import cz.levinzonr.studypad.removeIf
import cz.levinzonr.studypad.views
import kotlinx.android.synthetic.main.view_note_edition.view.*
import kotlinx.android.synthetic.main.view_publish_step_info.view.*

class AdditionalInfoStep(listener: StepViewClickListener) : BaseStep<PublishModels.StepTwoResult>(listener,"Step2", "Helo wosadaslmnfdsonmd;dsk kdan lksmdq") {



    override fun getStepResourceId(): Int = R.layout.view_publish_step_info

    override fun onStepViewCreated() {
        stepView.notebookTopicEt.setOnClickListener { listener?.onClick(it) }
        val addTagButton = Chip(context).apply {
            text = "Add Tag"
            setChipIconResource(R.drawable.ic_round_add_24px)
            setChipIconTintResource(R.color.blue)
        }
        addTagButton.setOnClickListener { listener?.onClick(it) }
        stepView.notebookTagsCG.addView(addTagButton)
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
    }

    fun toggleTag(tag: String, enable: Boolean) {
        if (!enable) {
            stepView.notebookTagsCG.removeIf { (it as Chip).text == tag }
        } else {
            stepView.notebookTagsCG.addView(Chip(context).apply { text = tag })

        }
        markAsCompletedOrUncompleted(true)
    }

}