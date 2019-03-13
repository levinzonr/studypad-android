package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.data.PublishNotebookRequest
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.rest.Api
import timber.log.Timber

class PublishedNotebookRepositoryImpl(private val api: Api) : PublishedNotebookRepository {

    override suspend fun getRelevantNotebooks(): List<PublishedNotebook.Feed> {
        return api.getRelevantNotebooks().await()
    }

    override suspend fun getPublishedNotebookDetail(id: String): PublishedNotebook.Detail {
        return api.getPublishedNotebookDetail(id).await()
    }

    override suspend fun publishNotebook(
        notebookId: String, title: String,
        description: String?, tags: Set<String>, topicId: Long?) : PublishedNotebook.Feed {

        val request = PublishNotebookRequest(
            title = title,
            notebookId = notebookId,
            tags = tags,
            topic = topicId,
            universityId = null,
            description = description
        )

        Timber.d("request: $request")
        return api.publishNotebook(request).await()
    }
}