package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.domain.models.PublishedNotebook

/**
 * Comments repository interface to operate on Comment model
 */
interface CommentsRepository {

    /**
     * Creates comment
     * @param notebookId - id of the notebook to where add the comment
     * @param body - body of the comment
     * @return newly created comment
     */
    suspend fun createComment(notebookId: String, body: String) : PublishedNotebook.Comment

    /**
     * Edit the existed comment
     * @param commentId - id of the comment
     * @param body - new body for this comment
     * @return the updated comment
     */
    suspend fun editComment(commentId: Long, body: String) : PublishedNotebook.Comment


    /**
     * Deletes comment by id
     * @param commentId - id of the comment to be deleted
     */
    suspend fun deleteComment(commentId: Long)

    /**
     * Returns the list of the comments from the specific notebook
     * @param notebookId - id of the notebook from where the comments are retrieved
     * @return List of the comments  for this notebook
     */
    suspend fun getComments(notebookId: String) : List<PublishedNotebook.Comment>
}