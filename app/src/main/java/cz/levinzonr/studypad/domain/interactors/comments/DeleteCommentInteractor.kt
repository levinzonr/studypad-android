package cz.levinzonr.studypad.domain.interactors.comments

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.repository.CommentsRepository

/**
 * Interactor to delete a comment
 * @param repository comments repository to handle request
 */
class DeleteCommentInteractor(private val repository: CommentsRepository) : BaseInputInteractor<Long, Unit>() {

    /**
     * Deletes a comment by id
     * @param input id of the comment to delete
     */
    override suspend fun executeOnBackground(input: Long) {
        return repository.deleteComment(input)
    }
}