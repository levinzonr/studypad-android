package cz.levinzonr.studypad.presentation.screens.library.publish

import cz.levinzonr.studypad.call
import cz.levinzonr.studypad.domain.interactors.PublishNotebookInteractor
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.liveEvent
import cz.levinzonr.studypad.presentation.base.BaseViewModel

class PublishNotebookViewModel(private val notebook: Notebook,
                               private val publishNotebookInteractor: PublishNotebookInteractor,
                               private val userManager: UserManager) : BaseViewModel() {

    val notebookPublishedEvent = liveEvent()

    var title = notebook.name
    var description = ""

    var tags : MutableSet<String> = mutableSetOf()



    fun publish() {
        toggleLoading(true)
        publishNotebookInteractor.executeWithInput(PublishNotebookInteractor.Input(notebook.id, title, description, tags)) {
            onComplete {
                toggleLoading(false)
                notebookPublishedEvent.call()
            }

            onError {
                postError(it.message)
            }
        }
    }

}