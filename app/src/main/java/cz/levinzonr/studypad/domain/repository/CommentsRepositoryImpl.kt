package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.rest.Api

class CommentsRepositoryImpl(private val api: Api) : CommentsRepository {


    override suspend fun createComment(notebookId: String, body: String): PublishedNotebook.Comment {
        return api.createComment(notebookId, body).await()
    }

    override suspend fun editComment(commentId: Long, body: String): PublishedNotebook.Comment {
        return api.editComment(commentId, body).await()
    }

    override suspend fun deleteComment(commentId: Long) {
        return api.deleteComment(commentId).await()
    }

    override suspend fun getComments(notebookId: String): List<PublishedNotebook.Comment> {
        return api.getSharedNotebookComments(notebookId).await()
    }
}