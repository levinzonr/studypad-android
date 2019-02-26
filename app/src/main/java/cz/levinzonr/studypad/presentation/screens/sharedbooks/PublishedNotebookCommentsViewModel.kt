package cz.levinzonr.studypad.presentation.screens.sharedbooks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.comments.CreateCommentInteractor
import cz.levinzonr.studypad.domain.interactors.comments.DeleteCommentInteractor
import cz.levinzonr.studypad.domain.interactors.comments.EditCommentInteractor
import cz.levinzonr.studypad.domain.interactors.comments.GetCommentsInteractor
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.Event

class PublishedNotebookCommentsViewModel(
    val notebookId: String,
    private val createCommentInteractor: CreateCommentInteractor,
    private val editCommentInteractor: EditCommentInteractor,
    private val deleteCommentInteractor: DeleteCommentInteractor,
    private val getCommentsInteractor: GetCommentsInteractor
) : BaseViewModel() {

    private val commentsLiveData = MutableLiveData<List<PublishedNotebook.Comment>>()
    val commentsStateLiveData = MutableLiveData<Event<CommentsState>>()

    init {
        toggleLoading(true)
        loadComments()
    }

    private fun loadComments() {
        getCommentsInteractor.executeWithInput(notebookId) {
            onComplete { commentsLiveData.postValue(it) }
        }
    }


    fun getCommentsLiveData() : LiveData<List<PublishedNotebook.Comment>> {
        return commentsLiveData
    }


    fun createComment(body: String) {
        createCommentInteractor.executeWithInput(CreateCommentInteractor.Input(notebookId, body)) {
            onComplete {
                commentsStateLiveData.postValue(Event(CommentsState(commentAdded = it)))
            }
        }
    }

    fun deleteComment(comment: PublishedNotebook.Comment) {
        deleteCommentInteractor.executeWithInput(comment.id) {
            onComplete {
                commentsStateLiveData.postValue(Event(CommentsState(commentDeleted = comment)))
            }
        }
    }

    fun editComment(comment: PublishedNotebook.Comment, newBody: String) {
        editCommentInteractor.executeWithInput(EditCommentInteractor.Input(comment.id, newBody)) {
            onComplete {
                commentsStateLiveData.postValue(Event(CommentsState(commentUpdated = it)))

            }
        }
    }
}
