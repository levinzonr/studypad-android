package cz.levinzonr.studyhub.domain.repository

import androidx.lifecycle.LiveData
import cz.levinzonr.studyhub.domain.Notebook

interface NotebookRepository {

    suspend fun getNotebooks() : List<Notebook>

}