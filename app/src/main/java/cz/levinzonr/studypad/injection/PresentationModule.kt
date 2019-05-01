package cz.levinzonr.studypad.injection

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.screens.publish.steps.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val presentation = module {

    factory {  (listener: BaseStep.StepViewClickListener) -> BasicStep(listener, androidContext().getString(R.string.publish_step1_title), androidContext().getString(R.string.publish_step1_message)) }

    factory { (listener: BaseStep.StepViewClickListener) ->AdditionalInfoStep(listener, androidContext().getString(R.string.publish_step2_title), androidContext().getString(R.string.publish_step2_message))}

    factory { (listener: BaseStep.StepViewClickListener) ->DescriptionStep(listener, androidContext().getString(R.string.publish_step3_title), androidContext().getString(R.string.publish_step3_message))}

    factory { (listner: BaseStep.StepViewClickListener ) ->  ConfirmationStep(listner, androidContext().getString(R.string.publish_step4_title), "") }
}