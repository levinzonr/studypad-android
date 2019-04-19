package cz.levinzonr.studypad.injection

import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.presentation.screens.library.publish.steps.*
import org.koin.dsl.module.module

val presentation = module {

    factory {  (listener: BaseStep.StepViewClickListener) -> BasicStep(listener) }

    factory { (listener: BaseStep.StepViewClickListener) ->AdditionalInfoStep(listener)}

    factory { (listener: BaseStep.StepViewClickListener) ->DescriptionStep(listener)}

    factory { (listner: BaseStep.StepViewClickListener ) ->  ConfirmationStep(listner) }
}