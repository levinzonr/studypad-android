package cz.levinzonr.studyhub.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studyhub.domain.Notebook
import kotlinx.coroutines.delay

class MockNotebookRepository : NotebookRepository {

    override suspend fun getNotebooks(): List<Notebook> {
        val list = mutableListOf<Notebook>()
        repeat(10) {
            list.add(it, Notebook(it.toLong(), "Notebook $it", it))
        }
        delay(1000)
        return list
    }
}