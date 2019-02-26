package cz.levinzonr.studypad.presentation.screens.sharedbooks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.comments.CreateCommentInteractor
import cz.levinzonr.studypad.domain.interactors.comments.DeleteCommentInteractor
import cz.levinzonr.studypad.domain.interactors.comments.EditCommentInteractor
import cz.levinzonr.studypad.domain.interactors.comments.GetCommentsInteractor
import cz.levinzonr.studypad.domain.interactors.sharinghub.GetPublishedNotebookDetail
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.Event
import java.util.*


class PublishedNotebookDetailViewModel(
    val notebookId: String,
    private val getPublishedNotebookDetail: GetPublishedNotebookDetail
) : BaseViewModel(){

    private val sharedDetailLiveData = MutableLiveData<PublishedNotebook.Detail>()
    private val commentsLiveData = MutableLiveData<List<PublishedNotebook.Comment>>()

    init {
        toggleLoading(true)
        getPublishedNotebookDetail.executeWithInput(notebookId) {
            onComplete {
                sharedDetailLiveData.postValue(it)
                toggleLoading(false)

            }
        }
    }

    fun getSharedDetailLiveData() : LiveData<PublishedNotebook.Detail> {
        return sharedDetailLiveData
    }

}