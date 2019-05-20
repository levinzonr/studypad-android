package cz.levinzonr.studypad.domain.interactors.comments

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.repository.CommentsRepository

/**
 * Interactor To Create a new Comment on Published Notebook
 * @param commentsRepository repository to handle request
 *
 */
class CreateCommentInteractor(private val commentsRepository: CommentsRepository) : BaseInputInteractor<CreateCommentInteractor.Input, PublishedNotebook.Comment>()  {

    /**
     * Interactor input
     * @param id of the published notebook
     * @param body comment's body
     * @return the comment created
     */
    data class Input(val id: String, val body: String)


    override suspend fun executeOnBackground(input: Input): PublishedNotebook.Comment {
        return commentsRepository.createComment(input.id, input.body)
    }
}