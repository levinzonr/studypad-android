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
import ernestoyaquello.com.verticalstepperform.Step
import kotlinx.android.synthetic.main.view_publish_step_info.view.*

class AdditionalInfoStep() : BaseStep<PublishModels.StepTwoResult>("Step2", "Helo wosadaslmnfdsonmd;dsk kdan lksmdq") {

    private lateinit var topicEt: TextInputEditText
    private lateinit var chipGroup: ChipGroup


    override fun isStepDataValid(stepData: PublishModels.StepTwoResult?): IsDataValid {
        return IsDataValid(true)
    }

    override fun createStepContentLayout(): View {
        val view = context.layoutInflater.inflate(R.layout.view_publish_step_info, null, false)
        topicEt = view.notebookTopicEt
        topicEt.setOnClickListener { listener?.onClick(it) }
        val addTagButton = Chip(context).apply {
            text = "Add Tag"
            setChipIconResource(R.drawable.ic_round_add_24px)
            setChipIconTintResource(R.color.blue)
        }
        addTagButton.setOnClickListener { listener?.onClick(it) }
        chipGroup = view.notebookTagsCG
        chipGroup.addView(addTagButton)
        return view

    }

    override fun getStepData(): PublishModels.StepTwoResult {
        val tags = chipGroup.views.map { (it as Chip).text.toString() }
        return PublishModels.StepTwoResult(topicEt.tag as? Topic?, tags.toMutableSet())
    }

    fun setTopic(topic: Topic) {
        topicEt.setText(topic.name)
        topicEt.tag = topic
    }

    fun toggleTag(tag: String, enable: Boolean) {
        if (!enable) {
            chipGroup.removeIf { (it as Chip).text == tag }
        } else {
            chipGroup.addView(Chip(context).apply { text = tag })

        }
    }

}