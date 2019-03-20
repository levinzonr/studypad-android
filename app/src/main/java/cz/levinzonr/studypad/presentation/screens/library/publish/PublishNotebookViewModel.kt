package cz.levinzonr.studypad.presentation.screens.library.publish

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.call
import cz.levinzonr.studypad.domain.interactors.sharinghub.GetTagsByNameInteractor
import cz.levinzonr.studypad.domain.interactors.sharinghub.PublishNotebookInteractor
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.liveEvent
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import timber.log.Timber

class PublishNotebookViewModel(
    private val notebook: Notebook,
    private val publishNotebookInteractor: PublishNotebookInteractor,
    private val getTagsByNameInteractor: GetTagsByNameInteractor,
    private val userManager: UserManager
) : BaseViewModel() {


    var title = notebook.name
    var description = ""
    var topic: Topic? = null

    var selectedTags = MutableLiveData<Set<String>>()
    init {
        selectedTags.postValue(setOf())
    }


    fun publish() {
        toggleLoading(true)
        publishNotebookInteractor.executeWithInput(
            PublishNotebookInteractor.Input(
                notebookId = notebook.id,
                title = title,
                description = description,
                tags = selectedTags.value?.toSet() ?: setOf(),
                topic = topic
            )
        ) {
            onComplete {
                toggleLoading(false)
                navigateBack()
            }

            onError {
                postError(it.message)
            }
        }
    }


    fun setTagSelected(tag: String, selected: Boolean) {
        Timber.d("Set tag selected: $tag, $selected")
        var currentSet = selectedTags.value ?: setOf()

        currentSet = if (selected) {
            currentSet.plusElement(tag)
        } else currentSet.minusElement(tag)

        selectedTags.postValue(currentSet)
        Timber.d("New set? ${currentSet}")
    }

}