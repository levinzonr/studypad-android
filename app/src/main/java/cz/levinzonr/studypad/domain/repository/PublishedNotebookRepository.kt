package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.domain.models.PublishedNotebook

interface PublishedNotebookRepository {

    suspend fun getRelevantNotebooks() : List<PublishedNotebook.Feed>

    suspend fun getPublishedNotebookDetail(id: String) : PublishedNotebook.Detail

    suspend fun publishNotebook(notebookId: String, title: String, description: String?, tags: Set<String>, topicId: Long?) : PublishedNotebook.Feed

}