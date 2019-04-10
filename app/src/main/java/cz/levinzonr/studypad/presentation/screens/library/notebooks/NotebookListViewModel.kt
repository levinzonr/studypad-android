package cz.levinzonr.studypad.presentation.screens.library.notebooks

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.call
import cz.levinzonr.studypad.domain.interactors.keychain.RefreshFirebaseTokenInteractor
import cz.levinzonr.studypad.domain.interactors.library.*
import cz.levinzonr.studypad.domain.interactors.sharinghub.QuiclPublishInteractor
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.repository.NotebookRepository
import cz.levinzonr.studypad.liveEvent
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.Event
import timber.log.Timber

class NotebookListViewModel(
    private val deleteNotebookInteractor: DeleteNotebookInteractor,
    private val updateNotebookInteractor: UpdateNotebookInteractor,
    private val notebookRepository: NotebookRepository,
    private val getNotebooksInteractor: GetNotebooksInteractor,
    private val refreshFirebaseTokenInteractor: RefreshFirebaseTokenInteractor,
    private val quiclPublishInteractor: QuiclPublishInteractor,
    private val librarySyncInteractor: LibrarySyncInteractor,
    private val postNoteookInteractor: PostNotebookInteractor
) : BaseViewModel() {


    val dataSource = notebookRepository.notebooksLiveData()
    val syncCompletedEvent = liveEvent()
    val notebookPublishedEven = MutableLiveData<Event<PublishedNotebook.Feed>>()

    init {
        synchronize()
        refreshFirebaseTokenInteractor.execute {
            onComplete { postError("Notifcations: $it") }
            onError { postError("Notification $it") }
        }
    }

    fun synchronize() {
        librarySyncInteractor.execute {
            onComplete {
                syncCompletedEvent.call()
            }
        }
    }

    fun createNewNotebook(name: String) {
        postNoteookInteractor.executeWithInput(name) {
            onComplete {
               Timber.d("Done")
            }
        }
    }


    fun updateNotebook(notebook: Notebook, name: String) {
        if (name != notebook.name) {
            updateNotebookInteractor.input = UpdateNotebookInteractor.Input(notebook.id, name)
            updateNotebookInteractor.execute {
                onComplete {
                  Timber.d("Updated")
                }
            }
        }
    }

    fun deleteNotebook(notebook: Notebook) {
        deleteNotebookInteractor.input = DeleteNotebookInteractor.Input(notebook.id)
        deleteNotebookInteractor.execute {
            onComplete {
                Timber.d("Updated")
            }
        }
    }

    fun publishNotebook(notebook: Notebook) {
        quiclPublishInteractor.executeWithInput(notebook.id) {
            onComplete {
                getNotebooksInteractor.execute {  }
                notebookPublishedEven.call(it)
            }
        }
    }

    // Navigation Events
    fun showNotes(notebook: Notebook) {
        navigateTo(NotebookListFragmentDirections.actionNotebookListFragmentToNotesListFragment(notebook))
    }

    fun showPublishedNotebookDetail(id: String) {
        Timber.d(id)
        navigateTo(NotebookListFragmentDirections.actionNotebookListFragmentToPublishedNotebookDetailFragment(id))
    }

    fun startPublishNotebookFlow(notebook: Notebook) {
        navigateTo(NotebookListFragmentDirections.actionNotebookListFragmentToPublishNotebookFragment(notebook))
    }

}
