package cz.levinzonr.studypad.presentation.screens.selectors.tag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.sharinghub.GetTagsByNameInteractor
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import timber.log.Timber

class TagSearchViewModel(private val gatTagsByNameInteractor: GetTagsByNameInteractor) : BaseViewModel() {

    private val tagsLiveData = MutableLiveData<Set<String>>()
    private val tagsQuery = MutableLiveData<String>()

    private val recentTagsLiveData = MutableLiveData<Set<String>?>()

    init {
        loadTags("")
    }

    private fun loadTags(query: String) {
        toggleLoading(true)
        gatTagsByNameInteractor.executeWithInput(query) {
            onComplete {
                Timber.d("on Complete :$it")
                toggleLoading(false)
                it.forEach {
                    when(it) {
                        is TagsModels.TagSection.Recent -> recentTagsLiveData.postValue(it.tags)
                        is TagsModels.TagSection.Default -> tagsLiveData.postValue(it.tags)
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