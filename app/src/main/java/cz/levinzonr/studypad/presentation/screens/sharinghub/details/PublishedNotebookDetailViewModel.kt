package cz.levinzonr.studypad.presentation.screens.sharinghub.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.interactors.library.GetNotebookVersionStateInteractor
import cz.levinzonr.studypad.domain.interactors.sharinghub.ApplyLocalChangesInteractor
import cz.levinzonr.studypad.domain.interactors.sharinghub.GetPublishedNotebookDetail
import cz.levinzonr.studypad.domain.interactors.sharinghub.ImportCopyInteractor
import cz.levinzonr.studypad.domain.interactors.sharinghub.ImportPublishedNotebookInteractor
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.models.State
import cz.levinzonr.studypad.liveEvent
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.Event


class PublishedNotebookDetailViewModel(
    val notebookId: String,
    private val applyLocalChangesInteractor: ApplyLocalChangesInteractor,
    private val getPublishedNotebookDetail: GetPublishedNotebookDetail,
    private val importPublishedNotebookInteractor: ImportPublishedNotebookInteractor,
    private val getNotebookVersionStateInteractor: GetNotebookVersionStateInteractor,
    private val importCopyInteractor: ImportCopyInteractor
) : BaseViewModel() {

    private val sharedDetailLiveData = MutableLiveData<PublishedNotebook.Detail>()
    val updated = MutableLiveData<Event<String>?>()
    val stateLiveData = MutableLiveData<State>()

    init {
        updated.postValue(null)
        toggleLoading(true)
        refreshAll()
    }

    fun refreshAll() {
        getPublishedNotebookDetail.executeWithInput(notebookId) {
            toggleLoading(true)
            onComplete {
                toggleLoading(false)
                sharedDetailLiveData.postValue(it)
                getNotebookVersionStateInteractor.executeWithInput(it) {
                    onComplete { stateLiveData.postValue(it) }
                    onError { handleApplicationError(it) }
                }

            }
            onError {
                toggleLoading(false)
                handleApplicationError(it)
            }
        }
    }

    fun getSharedDetailLiveData(): LiveData<PublishedNotebook.Detail> {
        return sharedDetailLiveData
    }

    fun handleSaveAction() {
        importPublishedNotebookInteractor.executeWithInput(notebookId) {
            onComplete {
                updated.postValue(Event(string(R.string.sharinghub_success_import)))
                refreshAll()
            }
            onError { handleApplicationError(it) }
        }
    }

    fun handleApplyChanges() {
        applyLocalChangesInteractor.executeWithInput(notebookId) {
            onComplete {
                stateLiveData.postValue(State.UpToDate)
                sharedDetailLiveData.postValue(it)
                refreshAll()
            }
            onError { handleApplicationError(it) }
        }
    }

    fun onShowAllSuggestionsClicked() {
        val authoredByMe = sharedDetailLiveData.value?.authoredByMe ?: false
        val modifications = sharedDetailLiveData.value?.versionState?.modifications ?: listOf()
        val notes = sharedDetailLiveData.value?.notes ?: listOf()
        navigateTo(
            PublishedNotebookDetailFragmentDirections.actionPublishedNotebookDetailFragmentToNotebookSuggestionsFragment(
                modifications.toTypedArray(),
                notes.toTypedArray(),
                notebookId,
                authoredByMe = authoredByMe
            )
        )
    }

    fun onUpdateNotebookClicked() {
        navigateTo(PublishedNotebookDetailFragmentDirections.actionPublishedNotebookDetailFragmentToPublishNotebookFragment(publishedId = notebookId))
    }

    fun onShowAllNotesClicked() {
        val notes = sharedDetailLiveData.value?.notes ?: listOf()
        navigateTo(
            PublishedNotebookDetailFragmentDirections.actionPublishedNotebookDetailFragmentToPublishedNotesListFragment(
                notes.map { Note(-1, it.title, it.content, notebookId) }.toTypedArray()
            )
        )
    }

    fun onImportCopyButtonClicked() {
        importCopyInteractor.executeWithInput(notebookId) {
            onComplete { updated.postValue(Event(string(R.string.sharinghub_success_import_copy))) }
            onError { handleApplicationError(it) }
        }
    }
}