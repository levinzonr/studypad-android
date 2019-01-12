package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.domain.repository.PublishedNotebookRepository
import timber.log.Timber

class PublishNotebookInteractor(private val publishedNotebookRepository: PublishedNotebookRepository) : BaseInputInteractor<PublishNotebookInteractor.Input, PublishedNotebook.Feed>() {

    data class Input(
        val notebookId: Long,
        val title: String,
        val description: String,
        val tags: Set<String> = setOf(),
        val topic: Topic?
    )

    override suspend fun executeOnBackground(input: Input): PublishedNotebook.Feed {
       Timber.d("Input: $input")
       return publishedNotebookRepository.publishNotebook(input.notebookId, input.title, input.description, input.tags, input.topic?.id)
    }
}