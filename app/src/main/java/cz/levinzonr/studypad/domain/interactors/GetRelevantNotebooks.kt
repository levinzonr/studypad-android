package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.repository.PublishedNotebookRepository

class GetRelevantNotebooks(private val publishedNotebookRepository: PublishedNotebookRepository) : BaseInteractor<List<PublishedNotebook.Feed>>() {

    override suspend fun executeOnBackground(): List<PublishedNotebook.Feed> {
        return publishedNotebookRepository.getRelevantNotebooks()
    }

}