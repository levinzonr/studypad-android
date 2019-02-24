package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.domain.models.PublishedNotebook

interface CommentsRepository {

    suspend fun createComment(notebookId: String, body: String) : PublishedNotebook.Comment

    suspend fun editComment(commentId: Long, body: String) : PublishedNotebook.Comment

    suspend fun deleteComment(commentId: Long)

    suspend fun getComments(notebookId: String) : List<PublishedNotebook.Comment>
}