package cz.levinzonr.studypad.presentation.screens.library.notebooks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import cz.levinzonr.studypad.domain.interactors.GetNotebooksInteractor
import cz.levinzonr.studypad.domain.Notebook
import cz.levinzonr.studypad.domain.interactors.PostNotebookInteractor

class NotebookListViewModel(private val getNotebooksInteractor: GetNotebooksInteractor,
                            private val postNoteookInteractor: PostNotebookInteractor) : ViewModel() {


    val dataSource = MutableLiveData<List<Notebook>>()

    init {
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
                dataSource.postValue(dataSource.value?.toMutableList()?.apply { add(it)})
            }
        }
    }

}
