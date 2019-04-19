package cz.levinzonr.studypad.domain.repository

import androidx.lifecycle.LiveData
import cz.levinzonr.studypad.data.SectionResponse
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.presentation.screens.sharinghub.search.NotebookSearchModels

interface PublishedNotebookRepository {

    suspend fun getRelevantNotebooks() : List<SectionResponse>

    suspend fun getPublishedNotebookDetail(id: String) : PublishedNotebook.Detail

    suspend fun publishNotebook(
        notebookId: String,
        title: String,
        description: String?,
        tags: Set<String>,
        topicId: Long?,
        languageCode: String, university: University?) : PublishedNotebook.Feed

    suspend fun updatePublishNotebook(
        notebookId: String,
        title: String,
        description: String?,
        tags: Set<String>,
        topicId: Long?,
        languageCode: String, university: University?) : PublishedNotebook.Feed

    suspend fun searchNotebooks(searchState: NotebookSearchModels.SearchState) : List<PublishedNotebook.Feed>

}