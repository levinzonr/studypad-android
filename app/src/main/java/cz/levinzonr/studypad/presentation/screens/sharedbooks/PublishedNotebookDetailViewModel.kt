package cz.levinzonr.studypad.presentation.screens.sharedbooks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.GetPublishedNotebookDetail
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.Event
import java.util.*

private const val string1 = "Vestibulum id ligula porta felis euismod semper."
private const val string2 = "Aenean lacinia bibendum nulla sed consec\ntetur. Maecenas fauinterdum.'nninterdu.cibus mollis interdum."

class PublishedNotebookDetailViewModel(
    notebookId: String,
    private val getPublishedNotebookDetail: GetPublishedNotebookDetail) : BaseViewModel(){

    private val sharedDetailLiveData = MutableLiveData<Event<PublishedNotebook.Detail>>()
    private val commentsLiveData = MutableLiveData<List<PublishedNotebook.Comment>>()

    init {
        toggleLoading(true)
        getPublishedNotebookDetail.executeWithInput(notebookId) {
            onComplete {
                sharedDetailLiveData.postValue(Event(it))
                toggleLoading(false)
                commentsLiveData.postValue(
                    List(10) { index ->
                        val comment = if (index % 2 == 0) string1 else string2
                        PublishedNotebook.Comment(index.toLong(), it.author, comment, Date().time)}
                )
            }
        }
    }

    fun getDetailsLoadedEvent() : LiveData<Event<PublishedNotebook.Detail>> {
        return sharedDetailLiveData
    }

    fun getCommentsLiveData() : LiveData<List<PublishedNotebook.Comment>> {
        return commentsLiveData
    }



}