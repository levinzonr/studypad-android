package cz.levinzonr.studypad.presentation.screens.publish

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.interactors.sharinghub.GetPublishedNotebookDetail
import cz.levinzonr.studypad.domain.interactors.sharinghub.GetTagsByNameInteractor
import cz.levinzonr.studypad.domain.interactors.sharinghub.PublishNotebookInteractor
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.ApplicationError
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.domain.models.ViewError
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import java.util.*

class PublishNotebookViewModel(
    private val notebook: Notebook? = null,
    private val publishedNotebookDetailId: String? = null,
    private val getPublishedNotebookDetail: GetPublishedNotebookDetail,
    private val publishNotebookInteractor: PublishNotebookInteractor,
    private val getTagsByNameInteractor: GetTagsByNameInteractor,
    private val userManager: UserManager
) : BaseViewModel() {

    private val viewState = MutableLiveData<PublishModels.PublishViewState>()
    init {
        postDefaultState()
    }

    val defaultStateObservable: LiveData<PublishModels.PublishViewState>
        get() = viewState

    fun publish(stepOneData: PublishModels.StepOneData, stepTwoData: PublishModels.StepTwoData, stepThreeData: PublishModels.StepThreeData) {
        toggleLoading(true)
        if (notebook != null) {
            publishNotebook(notebook, stepOneData, stepTwoData, stepThreeData)
        } else if(publishedNotebookDetailId != null) {
            updatePublished(publishedNotebookDetailId, stepOneData, stepTwoData, stepThreeData)
        }

    }

    fun onSelectUniversityClicked() {
        navigateTo(PublishNotebookFragmentDirections.actionGlobalUniversitySelectorFragment(true))
    }

    private fun publishNotebook(notebook: Notebook, stepOneData: PublishModels.StepOneData, stepTwoData: PublishModels.StepTwoData, stepThreeData: PublishModels.StepThreeData) {
        publishNotebookInteractor.executeWithInput(
            PublishNotebookInteractor.Input(
                notebookId = notebook.id,
                title = stepOneData.name,
                description = stepThreeData.description,
                tags = stepTwoData.tags.toSet(),
                topic = stepTwoData.topic,
                languageCode = stepOneData.langCode,
                university = stepOneData.school,
                isToUpdate = false
            )
        ) {
            onComplete {
                toggleLoading(false)
                navigateBack()
            }

            onError {
                toggleLoading(false)
                when(it) {
                    is ApplicationError.NetworkError -> handleApplicationError(it)
                    else -> showError(ViewError.DialogError(string(R.string.error_publish_title), string(R.string.error_publish_message)))
                }
            }
        }

    }

    private fun updatePublished(id: String, stepOneData: PublishModels.StepOneData, stepTwoData: PublishModels.StepTwoData, stepThreeData: PublishModels.StepThreeData) {
        publishNotebookInteractor.executeWithInput(
            PublishNotebookInteractor.Input(
                notebookId = id,
                title = stepOneData.name,
                description = stepThreeData.description,
                tags = stepTwoData.tags.toSet(),
                topic = stepTwoData.topic,
                languageCode = stepOneData.langCode,
                university = stepOneData.school,
                isToUpdate = true
            )
        ) {
            onComplete {
                toggleLoading(false)
                navigateBack()
            }

            onError {
                when(it) {
                    is ApplicationError.NetworkError -> handleApplicationError(it)
                    else -> showError(ViewError.DialogError(string(R.string.error_publish_title), string(R.string.error_publish_message)))
                }

            }
        }
    }



    private fun postDefaultState() {
        notebook?.let {
            val uni = userManager.getCurrentUserInfo()?.university
            val locale = userManager.getCurrentUserInfo()?.chosenLocale?.code ?: java.util.Locale.getDefault().isO3Language
            val stepOneData = PublishModels.StepOneData(it.name, uni, locale)
            viewState.postValue(PublishModels.PublishViewState(stepOneData))
            return
        }

        publishedNotebookDetailId?.let {
            getPublishedNotebookDetail.executeWithInput(it) {
                onComplete {
                    val stepOne = PublishModels.StepOneData(
                        it.title,
                        it.university,
                        it.languageCode ?: Locale.getDefault().isO3Language
                    )
                    val stepTwo = PublishModels.StepTwoData(null, it.tags.toMutableSet())
                    val stepThree = PublishModels.StepThreeData(it.description ?: "")
                    viewState.postValue(PublishModels.PublishViewState(stepOne, stepTwo, stepThree))
                }
                onError {
                    handleApplicationError(it)
                    navigateBack()
                }
            }
            return
        }
    }



}