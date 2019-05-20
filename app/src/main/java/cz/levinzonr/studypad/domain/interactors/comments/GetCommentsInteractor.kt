package cz.levinzonr.studypad.domain.interactors.comments

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.repository.CommentsRepository
import cz.levinzonr.studypad.domain.repository.PublishedNotebookRepository

/**
 * Interactor to retrieve the comments of the notebook
 * @param repository to handle the request
 */
class GetCommentsInteractor(val repository: PublishedNotebookRepository) : BaseInputInteractor<String, List<PublishedNotebook.Comment>>() {

    /**
     * Executes this interactor with an input
     * @param input -- id of the notebooks
     */
    override suspend fun executeOnBackground(input: String): List<PublishedNotebook.Comment> {
        return repository.getPublishedNotebookDetail(input).comments
    }
}