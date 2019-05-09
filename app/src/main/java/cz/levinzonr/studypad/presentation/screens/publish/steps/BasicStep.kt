package cz.levinzonr.studypad.presentation.screens.publish.steps

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.defaultLanguageCode
import cz.levinzonr.studypad.domain.models.Locale
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.hideKeyboard
import cz.levinzonr.studypad.onTextChanged
import cz.levinzonr.studypad.presentation.screens.publish.PublishModels
import kotlinx.android.synthetic.main.view_publish_step_basic.view.*
import timber.log.Timber

class BasicStep(stepViewClickListener: StepViewClickListener, title: String, content: String) : BaseStep<PublishModels.StepOneData>(stepViewClickListener,title, content) {


    override fun getStepResourceId(): Int = R.layout.view_publish_step_basic


    override fun onStepViewCreated() {
        stepView.notebookLanguageEt.setOnClickListener { listener?.onClick(it) }
        stepView.notebookSchoolEt.setOnClickListener { listener?.onClick(it) }
        stepView.notebookNameEt.onTextChanged {
            stepView.nextStepBtn.isEnabled = isStepDataValid
            markAsCompletedOrUncompleted(true)
        }
        stepView.nextStepBtn.setOnClickListener {
            stepView.notebookNameEt.hideKeyboard()
            formView.goToNextStep(true)
        }
    }

    override fun getStepData(): PublishModels.StepOneData {
        val uni = stepView.notebookSchoolEt.tag as? University?
        val locale = stepView.notebookLanguageEt.tag as? Locale?
        Timber.d("Stpe: ${stepView.notebookNameEt.text.toString()}")
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
        Timber.d("SET $university")
        if (checkInput) markAsCompletedOrUncompleted(true)
    }


    override fun setDefaultData(stepData: PublishModels.StepOneData) {
        stepView.notebookNameEt.setText(stepData.name)
        val locale: Locale = Locale(java.util.Locale(stepData.langCode).displayName, stepData.langCode)
        setLanguage(locale, false)
        stepData.school?.let { setUniversity(it ,false)}

    }
}