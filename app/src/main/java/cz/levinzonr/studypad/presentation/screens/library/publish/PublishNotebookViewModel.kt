package cz.levinzonr.studypad.presentation.screens.library.publish

import cz.levinzonr.studypad.domain.interactors.sharinghub.GetTagsByNameInteractor
import cz.levinzonr.studypad.domain.interactors.sharinghub.PublishNotebookInteractor
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.presentation.base.BaseViewModel

class PublishNotebookViewModel(
    private val notebook: Notebook,
    private val publishNotebookInteractor: PublishNotebookInteractor,
    private val getTagsByNameInteractor: GetTagsByNameInteractor,
    private val userManager: UserManager
) : BaseViewModel() {


    fun publish(stepOneData: PublishModels.StepOneResult, stepTwoData: PublishModels.StepTwoResult, stepThreeData: PublishModels.StepThreeResult) {

        toggleLoading(true)
        publishNotebookInteractor.executeWithInput(
            PublishNotebookInteractor.Input(
                notebookId = notebook.id,
                title = stepOneData.name,
                description = stepThreeData.description,
                tags = stepTwoData.tags.toSet(),
                topic = stepTwoData.topic,
                languageCode = stepOneData.langCode,
                university = stepOneData.school
            )
        ) {
            onComplete {
                toggleLoading(false)
                navigateBack()
            }

            onError {
            }
        }
    }



}