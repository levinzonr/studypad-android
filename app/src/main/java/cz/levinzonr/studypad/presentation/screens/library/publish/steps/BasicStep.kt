package cz.levinzonr.studypad.presentation.screens.library.publish.steps

import android.view.View
import android.widget.Toast
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Locale
import cz.levinzonr.studypad.domain.models.Location
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.presentation.screens.library.publish.PublishModels
import ernestoyaquello.com.verticalstepperform.Step
import kotlinx.android.synthetic.main.view_publish_step_basic.view.*

class BasicStep() : BaseStep<PublishModels.StepOneResult>("Step1", "Step 1 desciptiosn hello wronds") {

    override fun isStepDataValid(stepData: PublishModels.StepOneResult?): IsDataValid {
        return IsDataValid(true)
    }


    override fun getStepDataAsHumanReadableString(): String {
        return "hello"
    }

    override fun createStepContentLayout(): View {
        val view =  context.layoutInflater.inflate(R.layout.view_publish_step_basic, null, false)
        view.notebookLanguageEt.setOnClickListener { listener?.onClick(it) }
        view.notebookSchoolEt.setOnClickListener { listener?.onClick(it) }
        return view
    }

    override fun getStepData(): PublishModels.StepOneResult {
        return PublishModels.StepOneResult(
            "",
            University("full", Location("as", "sa"), 1),
            ""
        )
    }

    fun setLanguage(locale: Locale) {
        entireStepLayout.notebookLanguageEt.setText(locale.displayName)
        entireStepLayout.notebookLanguageEt.tag = locale
    }
}