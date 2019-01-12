package cz.levinzonr.studypad.presentation.screens.sharedbooks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.GetPublishedNotebookDetail
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.Event

class PublishedNotebookDetailViewModel(
    notebookId: String,
    private val getPublishedNotebookDetail: GetPublishedNotebookDetail) : BaseViewModel(){

    private val sharedDetailLiveData = MutableLiveData<Event<PublishedNotebook.Detail>>()

    init {
        toggleLoading(true)
        getPublishedNotebookDetail.executeWithInput(notebookId) {
            onComplete {
                sharedDetailLiveData.postValue(Event(it))
                toggleLoading(false)
            }
        }
    }

    fun getDetailsLoadedEvent() : LiveData<Event<PublishedNotebook.Detail>> {
        return sharedDetailLiveData
    }
}