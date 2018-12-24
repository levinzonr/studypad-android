package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.domain.Notebook

interface NotebookRepository {

    suspend fun getNotebooks() : List<Notebook>

    suspend fun createNotebook(name: String) : Notebook

}