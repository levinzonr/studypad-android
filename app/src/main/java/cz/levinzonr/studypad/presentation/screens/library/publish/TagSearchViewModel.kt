package cz.levinzonr.studypad.presentation.screens.library.publish

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.levinzonr.studypad.domain.interactors.sharinghub.GetTagsByNameInteractor

class TagSearchViewModel(private val gatTagsByNameInteractor: GetTagsByNameInteractor) : ViewModel() {

    private val tagsLiveData = MutableLiveData<Set<String>>()
    private val tagsQuery = MutableLiveData<String>()


    init {
       loadTags("")
    }

    private fun loadTags(query: String) {
        gatTagsByNameInteractor.executeWithInput(query) {
            onComplete { tagsLiveData.postValue(it) }
        }
    }


    fun getTagsObservable() : LiveData<Set<String>> {
        return tagsLiveData
    }

    fun setFindTagsQuery(query: String) {
        loadTags(query)
    }
}