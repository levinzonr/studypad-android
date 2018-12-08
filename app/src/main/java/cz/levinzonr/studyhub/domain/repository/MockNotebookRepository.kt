package cz.levinzonr.studyhub.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studyhub.domain.Notebook

class MockNotebookRepository : NotebookRepository {

    override fun getNotebooks(): LiveData<List<Notebook>> {
        val list = mutableListOf<Notebook>()
        repeat(10) {
            list.add(it, Notebook(it.toLong(), "Notebook $it", it))
        }
        return MutableLiveData<List<Notebook>>().apply { value = list }
    }
}