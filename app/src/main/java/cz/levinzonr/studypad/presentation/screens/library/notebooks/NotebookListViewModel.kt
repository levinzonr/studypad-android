package cz.levinzonr.studypad.presentation.screens.library.notebooks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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


    val dataSource = Transformations.map(notebookRepository.notebooksLiveData()) {
        it.sortedBy { it.name }
    }
    val notebookPublishedEven = MutableLiveData<Event<PublishedNotebook.Feed>>()

    init {
        synchronize()
    }

    private fun synchronize() {
        librarySyncInteractor.execute {
            onComplete {
            }
            onError {

            }
        }
    }

    fun refreshNotebooks() {
        getNotebooksInteractor.execute {  }
    }

    fun createNewNotebook(name: String) {
        postNoteookInteractor.executeWithInput(name) {
            onComplete {
               Timber.d("Done")
            }
            onError {
                handleApplicationError(it)
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
                onError { handleApplicationError(it) }
            }
        }
    }

    fun deleteNotebook(notebook: Notebook) {
        deleteNotebookInteractor.input = DeleteNotebookInteractor.Input(notebook.id)
        deleteNotebookInteractor.execute {
            onComplete {
                Timber.d("Updated")
            }
            onError { handleApplicationError(it) }
        }
    }

    fun publishNotebook(notebook: Notebook) {
        quiclPublishInteractor.executeWithInput(notebook.id) {
            onComplete {
                refreshNotebooks()
                notebookPublishedEven.call(it)
            }
            onError { handleApplicationError(it) }
        }
    }

    // Navigation Events
    fun onNotebookSelected(notebook: Notebook) {
        navigateTo(NotebookListFragmentDirections.actionNotebookListFragmentToNotesListFragment(notebook))
    }

    fun onShowPublishedNotebook(id: String) {
        Timber.d(id)
        navigateTo(NotebookListFragmentDirections.actionGlobalPublishedNotebookDetailFragment(id))
    }

    fun startPublishNotebookFlow(notebook: Notebook) {
        navigateTo(NotebookListFragmentDirections.actionNotebookListFragmentToPublishNotebookFragment(notebook))
    }

}
