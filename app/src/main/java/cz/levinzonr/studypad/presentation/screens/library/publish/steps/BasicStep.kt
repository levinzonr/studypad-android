package cz.levinzonr.studypad.presentation.screens.library.publish.steps

import android.view.View
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.Locale
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.onTextChanged
import cz.levinzonr.studypad.presentation.screens.library.publish.PublishModels
import kotlinx.android.synthetic.main.view_publish_step_basic.view.*

class BasicStep(private val userManager: UserManager, val notebook: Notebook, stepViewClickListener: StepViewClickListener) : BaseStep<PublishModels.StepOneResult>(stepViewClickListener,"#1 Basic details", "Here is what we already know about this notebooks, check if everything is ok") {


    override fun getStepResourceId(): Int = R.layout.view_publish_step_basic


    override fun onStepViewCreated() {
        setupDefaultParameters()
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

    override fun getStepData(): PublishModels.StepOneResult {
        val uni = stepView.notebookSchoolEt.tag as? University?
        val locale = stepView.notebookLanguageEt.tag as Locale
        return PublishModels.StepOneResult(stepView.notebookNameEt.text.toString(), uni, langCode = locale.code)
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

    private fun setupDefaultParameters() {
        userManager.getCurrentUserInfo()?.let {
            it.university?.let { setUniversity(it ,false)}
            setLanguage(it.chosenLocale, false)
            stepView.notebookNameEt.setText(notebook.name)
        }
    }
}