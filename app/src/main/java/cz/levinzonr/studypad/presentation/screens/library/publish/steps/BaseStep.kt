package cz.levinzonr.studypad.presentation.screens.library.publish.steps

import android.view.View
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.presentation.screens.library.publish.Validatable
import ernestoyaquello.com.verticalstepperform.Builder
import ernestoyaquello.com.verticalstepperform.Step
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormView
import timber.log.Timber

abstract class BaseStep<T: Validatable>(val listener: StepViewClickListener?, title: String, content: String = "") : Step<T>(title, content){


    protected lateinit var stepView: View

    override fun restoreStepData(data: T) {
    }

    override fun getStepDataAsHumanReadableString(): String {
        return "Not Implemented"
    }


    override fun isStepDataValid(stepData: T): IsDataValid {
        Timber.d("IsValid: $stepData")
        val valid = stepData.isValid()
        return IsDataValid(valid)
    }

    override fun onStepOpened(animated: Boolean) {
    }

    override fun onStepMarkedAsUncompleted(animated: Boolean) {
    }

    override fun onStepClosed(animated: Boolean) {
    }

    override fun onStepMarkedAsCompleted(animated: Boolean) {

    }

    override fun createStepContentLayout(): View {
        return context.layoutInflater.inflate(getStepResourceId(), null, false)
            .also {
                stepView = it
                onStepViewCreated()
            }
    }

    protected abstract fun onStepViewCreated()

    protected abstract fun getStepResourceId() : Int

    interface StepViewClickListener {
        fun onClick(view: View)
    }

}