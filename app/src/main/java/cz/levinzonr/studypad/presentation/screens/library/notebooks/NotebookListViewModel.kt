package cz.levinzonr.studypad.presentation.screens.library.notebooks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import cz.levinzonr.studypad.domain.interactors.DeleteNotebookInteractor
import cz.levinzonr.studypad.domain.interactors.GetNotebooksInteractor
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.domain.interactors.PostNotebookInteractor
import cz.levinzonr.studypad.domain.interactors.UpdateNotebookInteractor
import cz.levinzonr.studypad.presentation.base.BaseViewModel

class NotebookListViewModel(
    private val deleteNotebookInteractor: DeleteNotebookInteractor,
    private val updateNotebookInteractor: UpdateNotebookInteractor,
    private val getNotebooksInteractor: GetNotebooksInteractor,
    private val postNoteookInteractor: PostNotebookInteractor
) : BaseViewModel() {


    val dataSource = MutableLiveData<List<Notebook>>()

    init {
      loadNotebooks()
    }

    private fun loadNotebooks() {
        getNotebooksInteractor.execute {
            onComplete {
                dataSource.postValue(it)
            }
        }
    }

    fun createNewNotebook(name: String) {
        postNoteookInteractor.input = PostNotebookInteractor.Input(name)
        postNoteookInteractor.execute {
            onComplete {
                dataSource.postValue(dataSource.value?.toMutableList()?.apply { add(it) })
            }
        }
    }


    fun updateNotebook(notebook: Notebook, name: String) {
        if (name != notebook.name) {
            updateNotebookInteractor.input = UpdateNotebookInteractor.Input(notebook.id, name)
            updateNotebookInteractor.execute {
                onComplete {
                    loadNotebooks()
                }
            }
        }
    }

    fun deleteNotebook(notebook: Notebook) {
        deleteNotebookInteractor.input = DeleteNotebookInteractor.Input(notebook.id)
        deleteNotebookInteractor.execute {
            onComplete {
                loadNotebooks()
            }
        }
    }
}
