package cz.levinzonr.studypad.presentation.screens.selectors.topic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.sharinghub.GetTopicsInteractor
import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.presentation.base.BaseViewModel

class TopicSearchViewModel(private val getTopicsInteractor: GetTopicsInteractor) : BaseViewModel() {

    private var list: List<Topic> = listOf()
    private val topicLiveData = MutableLiveData<List<Topic>>()

    init {
        getTopicsInteractor.executeWithInput("") {
            toggleLoading(true)
            onComplete {
                toggleLoading(false)
                list = it
                topicLiveData.postValue(it)
            }
            onError { handleApplicationError(it) }
        }
    }

    fun filterTopics(query: String) {
        topicLiveData.postValue(list.filter { it.name.contains(query, ignoreCase = true) })
    }

    fun getTopicsObservable(): LiveData<List<Topic>> {
        return topicLiveData
    }
}
