package cz.levinzonr.studypad.presentation.screens.library.publish

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.levinzonr.studypad.domain.interactors.sharinghub.GetTopicsInteractor
import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.presentation.base.BaseViewModel

class TopicSearchViewModel(private val getTopicsInteractor: GetTopicsInteractor) : BaseViewModel() {

    private val topicLiveData = MutableLiveData<List<Topic>>()

    init {
        getTopicsInteractor.executeWithInput("" ) {
            onComplete {
                topicLiveData.postValue(it)
            }
            onError { handleApplicationError(it) }
        }
    }

    fun filterTopics(query: String) {
        topicLiveData.postValue(topicLiveData.value?.filter { it.name.contains(query) })
    }

    fun getTopicsObservable() : LiveData<List<Topic>> {
        return topicLiveData
    }
}