package cz.levinzonr.studyhub.presentation.screens.library

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import cz.levinzonr.studyhub.domain.GetNotebooksInteractor
import cz.levinzonr.studyhub.domain.Notebook
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NotebookListViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val dataSource = MutableLiveData<List<Notebook>>()
    init {
        runInteractor {
            dataSource.postValue(GetNotebooksInteractor().run())
        }
    }



    private fun runInteractor(block: suspend () -> Unit) {
        uiScope.launch {
            block.invoke()
        }
    }

}
