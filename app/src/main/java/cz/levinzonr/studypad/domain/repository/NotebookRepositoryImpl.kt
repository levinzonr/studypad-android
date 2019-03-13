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

    override suspend fun updateNotebook(id: String, name: String): Notebook {
        val notebook = remoteDataSource.updateNotebook(id, UpdateNotebookRequest(name)).await()
        localDataSource.notebookDao().put(notebook)
        return notebook
    }

    override suspend fun deleteNotebook(id: String) {
        remoteDataSource.deleteNotebook(id).await()
        deleteLocally(id)
    }

    override fun notebooksLiveData(): LiveData<List<Notebook>> {
        return localDataSource.notebookDao().getAllLiveData()
    }

    override fun getStoredNotebooks(): List<Notebook> {
        return localDataSource.notebookDao().getAll()
    }

    override fun deleteLocally(id: String) {
        localDataSource.notebookDao().delete(id)
    }

    override suspend fun importNotebook(id: String): Notebook {
        val created = remoteDataSource.importPublisheNotebook(id).await()
        localDataSource.notebookDao().put(created)
        return created
    }
}