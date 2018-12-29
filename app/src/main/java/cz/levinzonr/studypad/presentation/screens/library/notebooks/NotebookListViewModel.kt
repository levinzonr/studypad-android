package cz.levinzonr.studypad.presentation.screens.library.notebooks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import cz.levinzonr.studypad.domain.interactors.DeleteNotebookInteractor
import cz.levinzonr.studypad.domain.interactors.GetNotebooksInteractor
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.domain.interactors.PostNotebookInteractor
import cz.levinzonr.studypad.domain.interactors.UpdateNotebookInteractor
import cz.levinzonr.studypad.domain.repository.NotebookRepository
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import timber.log.Timber

class NotebookListViewModel(
    private val deleteNotebookInteractor: DeleteNotebookInteractor,
    private val updateNotebookInteractor: UpdateNotebookInteractor,
    private val notebookRepository: NotebookRepository,
    getNotebooksInteractor: GetNotebooksInteractor,
    private val postNoteookInteractor: PostNotebookInteractor
) : BaseViewModel() {


    val dataSource = notebookRepository.notebooksLiveData()

    init {
        getNotebooksInteractor.execute {  }
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
}
