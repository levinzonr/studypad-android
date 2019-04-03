package cz.levinzonr.studypad.injection

import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.presentation.screens.library.publish.steps.AdditionalInfoStep
import cz.levinzonr.studypad.presentation.screens.library.publish.steps.BaseStep
import cz.levinzonr.studypad.presentation.screens.library.publish.steps.BasicStep
import cz.levinzonr.studypad.presentation.screens.library.publish.steps.DescriptionStep
import org.koin.dsl.module.module

val presentation = module {

    factory { (notebook: Notebook, listener: BaseStep.StepViewClickListener) ->BasicStep(get(), notebook, listener)}

    factory { (listener: BaseStep.StepViewClickListener) ->AdditionalInfoStep(listener)}

    factory { (listener: BaseStep.StepViewClickListener) ->DescriptionStep(listener)}


}