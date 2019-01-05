package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.data.PublishNotebookRequest
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.rest.Api

class PublishedNotebookRepositoryImpl(private val api: Api) : PublishedNotebookRepository {

    override suspend fun getRelevantNotebooks(): List<PublishedNotebook.Feed> {
        return api.getRelevantNotebooks().await()
    }

    override suspend fun getPublishedNotebookDetail(id: String): PublishedNotebook.Detail {
        return api.getPublishedNotebookDetail(id).await()
    }

    override suspend fun publishNotebook(
        notebookId: Long, title: String,
        description: String?, tags: Set<String>) : PublishedNotebook.Feed {

        val request = PublishNotebookRequest(
            title = title,
            notebookId = notebookId,
            tags = tags,
            topic = null,
            universityId = null,
            description = description
        )

        return api.publishNotebook(request).await()
    }
}