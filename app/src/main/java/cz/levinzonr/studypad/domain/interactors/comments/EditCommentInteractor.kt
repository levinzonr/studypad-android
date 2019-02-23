package cz.levinzonr.studypad.domain.interactors.comments

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.repository.CommentsRepository

class EditCommentInteractor(private val repository: CommentsRepository) : BaseInputInteractor<EditCommentInteractor.Input, PublishedNotebook.Comment>() {

    data class Input(val id: Long, val body: String)

    override suspend fun executeOnBackground(input: Input): PublishedNotebook.Comment {
        return repository.editComment(input.id, input.body)
    }
}