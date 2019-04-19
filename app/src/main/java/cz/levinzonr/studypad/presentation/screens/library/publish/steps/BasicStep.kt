package cz.levinzonr.studypad.presentation.screens.library.publish.steps

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.defaultLanguageCode
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.Locale
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.onTextChanged
import cz.levinzonr.studypad.presentation.screens.library.publish.PublishModels
import kotlinx.android.synthetic.main.view_publish_step_basic.view.*

class BasicStep(stepViewClickListener: StepViewClickListener) : BaseStep<PublishModels.StepOneData>(stepViewClickListener,"Basic details", "Here is what we already know about this notebook, check if everything is ok") {


    override fun getStepResourceId(): Int = R.layout.view_publish_step_basic


    override fun onStepViewCreated() {
        stepView.notebookLanguageEt.setOnClickListener { listener?.onClick(it) }
        stepView.notebookSchoolEt.setOnClickListener { listener?.onClick(it) }
        stepView.notebookNameEt.onTextChanged {
            stepView.nextStepBtn.isEnabled = isStepDataValid
            markAsCompletedOrUncompleted(true)
        }
        stepView.nextStepBtn.setOnClickListener {
            formView.goToNextStep(true)
        }
    }

    override fun getStepData(): PublishModels.StepOneData {
        val uni = stepView.notebookSchoolEt.tag as? University?
        val locale = stepView.notebookLanguageEt.tag as? Locale?
        return PublishModels.StepOneData(stepView.notebookNameEt.text.toString(), uni, langCode = locale?.code ?: defaultLanguageCode() )
    }

    fun setLanguage(locale: Locale, checkInput: Boolean = false) {
        stepView.notebookLanguageEt.setText(locale.displayName)
        stepView.notebookLanguageEt.tag = locale
        if (checkInput) markAsCompletedOrUncompleted(true)
    }

    fun setUniversity(university: University, checkInput: Boolean = false) {
        stepView.notebookSchoolEt.tag = university
        stepView.notebookSchoolEt.setText(university.fullName)
        if (checkInput) markAsCompletedOrUncompleted(true)
    }


    override fun setDefaultData(stepData: PublishModels.StepOneData) {
        stepView.notebookNameEt.setText(stepData.name)
        val locale: Locale = Locale(java.util.Locale(stepData.langCode).displayName, stepData.langCode)
        setLanguage(locale, false)
        stepData.school?.let { setUniversity(it ,false)}

    }
}