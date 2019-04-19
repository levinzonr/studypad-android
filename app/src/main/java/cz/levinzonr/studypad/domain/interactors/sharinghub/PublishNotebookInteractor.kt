package cz.levinzonr.studypad.domain.interactors.sharinghub

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.domain.repository.PublishedNotebookRepository
import cz.levinzonr.studypad.domain.repository.TagsRepository
import timber.log.Timber

class PublishNotebookInteractor(
    private val tagsRepository: TagsRepository,
    private val publishedNotebookRepository: PublishedNotebookRepository
) : BaseInputInteractor<PublishNotebookInteractor.Input, PublishedNotebook.Feed>() {

    data class Input(
        val isToUpdate: Boolean,
        val notebookId: String,
        val title: String,
        val description: String,
        val tags: Set<String> = setOf(),
        val topic: Topic?,
        val languageCode: String,
        val university: University?
    )

    override suspend fun executeOnBackground(input: Input): PublishedNotebook.Feed {
        Timber.d("Input: $input")
        if (!input.isToUpdate) {
            val result = publishedNotebookRepository.publishNotebook(
                input.notebookId,
                input.title,
                input.description,
                input.tags,
                input.topic?.id,
                university = input.university,
                languageCode = input.languageCode
            )
            tagsRepository.markAsChosen(input.tags.toList())
            return result
        } else {
            val result = publishedNotebookRepository.updatePublishNotebook(
                input.notebookId,
                input.title,
                input.description,
                input.tags,
                input.topic?.id,
                university = input.university,
                languageCode = input.languageCode
            )
            tagsRepository.markAsChosen(input.tags.toList())
            return result
        }
    }
}