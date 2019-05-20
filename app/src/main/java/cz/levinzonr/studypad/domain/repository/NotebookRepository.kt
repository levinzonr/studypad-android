package cz.levinzonr.studypad.domain.repository

import androidx.lifecycle.LiveData
import cz.levinzonr.studypad.data.NotebooksResponse
import cz.levinzonr.studypad.domain.models.Notebook

/**
 * Repository to deal with the notebook entitiy
 */
interface NotebookRepository {


    /**
     * Retrieves the notebooks from the local library
     * @return list of the notebooks
     */
    suspend fun getNotebooks() : List<NotebooksResponse>

    /**
     * Create a new notebooks with the given name
     * @param name the name of the notebook
     * @return Newly created notebook
     */
    suspend fun createNotebook(name: String) : Notebook

    /**
     * Updates the notebook's name by ID
     * @param id of the notebook to update
     * @param name - new name to set for a notebook
     * @return the updated notebook
     */
    suspend fun updateNotebook(id: String, name: String) : Notebook

    /**
     * Deletes a notebook by id
     * @param id of the notebook by id
     */
    suspend fun deleteNotebook(id: String)


    /**
     * Creates/Updates the local notebook from the published version
     * @param id of the notebook to import
     */
    suspend fun importNotebook(id: String) : Notebook

    /**
     * Creates the independent local notebook from the published version
     * @param id of the notebook to create the copy of
     */
    suspend fun importAsCopy(id: String) : Notebook

    fun notebooksLiveData() : LiveData<List<Notebook>>

    fun getStoredNotebooks() : List<Notebook>

    fun deleteLocally(id: String)
}