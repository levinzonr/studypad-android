package cz.levinzonr.studypad.domain.interactors.comments

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.repository.CommentsRepository
import cz.levinzonr.studypad.domain.repository.PublishedNotebookRepository

class GetCommentsInteractor(val repository: PublishedNotebookRepository) : BaseInputInteractor<String, List<PublishedNotebook.Comment>>() {

    override suspend fun executeOnBackground(input: String): List<PublishedNotebook.Comment> {
        return repository.getPublishedNotebookDetail(input).comments
    }
}