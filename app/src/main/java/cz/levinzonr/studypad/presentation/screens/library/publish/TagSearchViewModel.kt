package cz.levinzonr.studypad.presentation.screens.library.publish

import android.nfc.Tag
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.levinzonr.studypad.domain.interactors.sharinghub.GetTagsByNameInteractor
import cz.levinzonr.studypad.presentation.base.BaseViewModel

class TagSearchViewModel(private val gatTagsByNameInteractor: GetTagsByNameInteractor) : BaseViewModel() {

    private val tagsLiveData = MutableLiveData<Set<String>>()
    private val tagsQuery = MutableLiveData<String>()

    private val recentTagsLiveData = MutableLiveData<Set<String>?>()

    init {
        loadTags("")
    }

    private fun loadTags(query: String) {
        gatTagsByNameInteractor.executeWithInput(query) {
            onComplete {
                it.forEach {
                    when(it) {
                        is TagsModels.TagSection.Recent -> recentTagsLiveData.postValue(it.tags)
                        is TagsModels.TagSection.Default -> tagsLiveData.postValue(List(100) { "Tag $it"}.toSet())
                    }
                }
                if (!it.any { it is TagsModels.TagSection.Recent }) {
                    recentTagsLiveData.postValue(null)
                }
            }
            onError {
                handleApplicationError(it)
            }
        }
    }


    fun getTagsObservable(): LiveData<Set<String>> {
        return tagsLiveData
    }

    fun getResentObservable(): LiveData<Set<String>?> {
        return recentTagsLiveData
    }

    fun setFindTagsQuery(query: String) {
        loadTags(query)
    }
}