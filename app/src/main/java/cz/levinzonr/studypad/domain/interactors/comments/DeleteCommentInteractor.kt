package cz.levinzonr.studypad.domain.interactors.comments

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.repository.CommentsRepository

class DeleteCommentInteractor(private val repository: CommentsRepository) : BaseInputInteractor<Long, Unit>() {

    override suspend fun executeOnBackground(input: Long) {
        return repository.deleteComment(input)
    }
}