package cz.levinzonr.studypad.presentation.screens.library.publish

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.levinzonr.studypad.domain.interactors.GetTagsInteractor

class TagSearchViewModel(private val gatTagsInteractor: GetTagsInteractor) : ViewModel() {

    private val tagsLiveData = MutableLiveData<Set<String>>()
    private val tagsQuery = MutableLiveData<String>()


    init {
       loadTags("")
    }

    private fun loadTags(query: String) {
        gatTagsInteractor.executeWithInput(query) {
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