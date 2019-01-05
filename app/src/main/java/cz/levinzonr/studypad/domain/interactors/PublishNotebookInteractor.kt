package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.repository.PublishedNotebookRepository

class PublishNotebookInteractor(private val publishedNotebookRepository: PublishedNotebookRepository) : BaseInputInteractor<PublishNotebookInteractor.Input, PublishedNotebook.Feed>() {

    data class Input(
        val notebookId: Long,
        val title: String,
        val description: String? = null,
        val tags: Set<String> = setOf()
    )

    override suspend fun executeOnBackground(input: Input): PublishedNotebook.Feed {
       return publishedNotebookRepository.publishNotebook(input.notebookId, input.title, input.description, input.tags)
    }
}