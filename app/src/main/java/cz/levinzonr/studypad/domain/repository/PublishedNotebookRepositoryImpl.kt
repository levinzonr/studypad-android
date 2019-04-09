package cz.levinzonr.studypad.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tyro.oss.arbitrater.arbitraryInstance
import cz.levinzonr.studypad.data.PublishNotebookRequest
import cz.levinzonr.studypad.data.SectionResponse
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.presentation.screens.sharinghub.search.NotebookSearchModels
import cz.levinzonr.studypad.rest.Api
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

class PublishedNotebookRepositoryImpl(private val api: Api) : PublishedNotebookRepository {

    override suspend fun getRelevantNotebooks(): List<SectionResponse> {
        return api.getRelevantNotebooksAsync().await()
    }

    override suspend fun getPublishedNotebookDetail(id: String): PublishedNotebook.Detail {
        return api.getPublishedNotebookDetailAsync(id).await()
    }

    override suspend fun publishNotebook(
        notebookId: String,
        title: String,
        description: String?,
        tags: Set<String>,
        topicId: Long?,
        languageCode: String,
        university: University?
    ): PublishedNotebook.Feed {

        val request = PublishNotebookRequest(
            title = title,
            notebookId = notebookId,
            tags = tags,
            topic = topicId,
            universityId = university?.id,
            description = description,
            languageCode = languageCode
        )

        Timber.d("request: $request")
        return api.publishNotebookAsync(request).await()

    }

    override suspend fun searchNotebooks(searchState: NotebookSearchModels.SearchState): List<PublishedNotebook.Feed> {
        return api.findNotebooksAsync(
            query = searchState.query,
            topic = searchState.topic.map { it.id },
            tags = searchState.tags.toSet(),
            universityId = searchState.university?.id
        ).await()
    }


}