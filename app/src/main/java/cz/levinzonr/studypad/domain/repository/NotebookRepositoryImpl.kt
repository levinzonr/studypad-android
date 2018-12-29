package cz.levinzonr.studypad.domain.repository

import androidx.lifecycle.LiveData
import cz.levinzonr.studypad.data.CreateNotebookRequest
import cz.levinzonr.studypad.data.UpdateNotebookRequest
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.rest.Api
import cz.levinzonr.studypad.storage.database.AppDatabase

class NotebookRepositoryImpl(
    private val localDataSource: AppDatabase,
    private val remoteDataSource: Api
) : NotebookRepository {


    override suspend fun getNotebooks(): List<Notebook> {
        val list = remoteDataSource.getNotebooks().await()
        localDataSource.notebookDao().putAll(list)
        return list
    }

    override suspend fun createNotebook(name: String): Notebook {
        val notebook = remoteDataSource.postNotebook(CreateNotebookRequest(name)).await()
        localDataSource.notebookDao().put(notebook)
        return notebook
    }

    override suspend fun updateNotebook(id: Long, name: String): Notebook {
        val notebook = remoteDataSource.updateNotebook(id, UpdateNotebookRequest(name)).await()
        localDataSource.notebookDao().put(notebook)
        return notebook
    }

    override suspend fun deleteNotebook(id: Long) {
        remoteDataSource.deleteNotebook(id).await()
        localDataSource.notebookDao().delete(id)
    }

    override fun notebooksLiveData(): LiveData<List<Notebook>> {
        return localDataSource.notebookDao().getAll()
    }
}