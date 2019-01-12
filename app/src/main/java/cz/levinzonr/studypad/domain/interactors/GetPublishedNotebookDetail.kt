package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.repository.PublishedNotebookRepository
import cz.levinzonr.studypad.rest.Api

class GetPublishedNotebookDetail(private val repository: PublishedNotebookRepository) : BaseInputInteractor<String, PublishedNotebook.Detail>() {

    override suspend fun executeOnBackground(input: String): PublishedNotebook.Detail {
        return repository.getPublishedNotebookDetail(input)
    }
}