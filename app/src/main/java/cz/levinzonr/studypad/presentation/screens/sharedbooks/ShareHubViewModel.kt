package cz.levinzonr.studypad.presentation.screens.sharedbooks

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.GetRelevantNotebooks
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.presentation.base.BaseViewModel

class ShareHubViewModel(private val getRelevantNotebooks: GetRelevantNotebooks) : BaseViewModel() {

    val dataSource = MutableLiveData<List<PublishedNotebook.Feed>>()

    init {
        toggleLoading(true)
        getRelevantNotebooks.execute {
            onComplete {
                toggleLoading(false)
                dataSource.postValue(it)
            }
            onError {
                postError(it.message)
            }
        }
    }



}