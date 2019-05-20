package cz.levinzonr.studypad.domain.interactors.comments

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.repository.CommentsRepository

/**
 * Interactor to edit a comment by id
 * @param repository to handle a request
 */
class EditCommentInteractor(private val repository: CommentsRepository) : BaseInputInteractor<EditCommentInteractor.Input, PublishedNotebook.Comment>() {

    /**
     * Interactor's Input
     * @param id -- id of the comment to edit
     * @param body -- new comment's body
     */
    data class Input(val id: Long, val body: String)

    /**
     * Executes interactor with input
     * @param input -- input
     * @return the comment updated
     */
    override suspend fun executeOnBackground(input: Input): PublishedNotebook.Comment {
        return repository.editComment(input.id, input.body)
    }
}