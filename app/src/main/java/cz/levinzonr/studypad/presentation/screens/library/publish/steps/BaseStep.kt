package cz.levinzonr.studypad.presentation.screens.library.publish.steps

import android.view.View
import cz.levinzonr.studypad.layoutInflater
import ernestoyaquello.com.verticalstepperform.Builder
import ernestoyaquello.com.verticalstepperform.Step
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormView

abstract class BaseStep<T>(title: String, content: String = "") : Step<T>(title, content){

    var listener: StepViewClickListener? = null

    override fun restoreStepData(data: T) {
    }

    override fun getStepDataAsHumanReadableString(): String {
        return "Not Implemented"
    }


    override fun onStepOpened(animated: Boolean) {
    }

    override fun onStepMarkedAsUncompleted(animated: Boolean) {
    }

    override fun onStepClosed(animated: Boolean) {
    }

    override fun onStepMarkedAsCompleted(animated: Boolean) {

    }

    interface StepViewClickListener {
        fun onClick(view: View)
    }

}