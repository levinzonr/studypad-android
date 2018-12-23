package cz.levinzonr.studyhub.domain.repository

import androidx.lifecycle.LiveData
import cz.levinzonr.studyhub.domain.Notebook

interface NotebookRepository {

    fun getNotebooks() : List<Notebook>

}