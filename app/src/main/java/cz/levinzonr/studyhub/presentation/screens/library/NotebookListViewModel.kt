package cz.levinzonr.studyhub.presentation.screens.library

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import cz.levinzonr.studyhub.domain.interactors.GetNotebooksInteractor
import cz.levinzonr.studyhub.domain.Notebook

class NotebookListViewModel(private val getNotebooksInteractor: GetNotebooksInteractor) : ViewModel() {


    val dataSource = MutableLiveData<List<Notebook>>()

    init {
        getNotebooksInteractor.execute {
            onComplete {
                dataSource.postValue(it)
            }
        }
    }
}
