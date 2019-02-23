package cz.levinzonr.studypad.domain.interactors.comments

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.repository.CommentsRepository

class CreateCommentInteractor(private val commentsRepository: CommentsRepository) : BaseInputInteractor<CreateCommentInteractor.Input, PublishedNotebook.Comment>()  {

    data class Input(val id: String, val body: String)


    override suspend fun executeOnBackground(input: Input): PublishedNotebook.Comment {
        return commentsRepository.createComment(input.id, input.body)
    }
}