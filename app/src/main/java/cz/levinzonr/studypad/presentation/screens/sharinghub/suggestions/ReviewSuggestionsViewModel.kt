package cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.sharinghub.SubmitReviewInteractor
import cz.levinzonr.studypad.indexOfFirstOrNull
import cz.levinzonr.studypad.presentation.base.BaseViewModel

class ReviewSuggestionsViewModel(
    suggestions: List<SuggestionsModels.SuggestionItem>,
    private val submitReviewInteractor: SubmitReviewInteractor) : BaseViewModel() {

    val suggestionsLiveData = MutableLiveData<List<SuggestionsModels.SuggestionItem>>()
    init {
        suggestionsLiveData.postValue(suggestions)
    }

     fun onApproveSuggestionClicked(suggestionItem: SuggestionsModels.SuggestionItem) {
        val index = suggestionsLiveData.value?.indexOfFirstOrNull { suggestionItem == it } ?: return
        val list = suggestionsLiveData.value?.toMutableList() ?: mutableListOf()
        val approved = !list[index].approved
        list[index] = suggestionItem.copy(approved = approved, rejected = false)
        suggestionsLiveData.postValue(list)
    }

     fun onRejectSuggestionClicked(suggestionItem: SuggestionsModels.SuggestionItem) {
        val index = suggestionsLiveData.value?.indexOfFirstOrNull { suggestionItem == it } ?: return
        val list = suggestionsLiveData.value?.toMutableList() ?: mutableListOf()
        val approved = !list[index].rejected
        list[index] = suggestionItem.copy(rejected = approved, approved = false)
        suggestionsLiveData.postValue(list)
    }


    fun onSumbitReviewBtnClicked() {
        val approved = suggestionsLiveData.value?.filter { it.approved }?.map { it.suggestion }
        val rejected =  suggestionsLiveData.value?.filter { it.rejected }?.map { it.suggestion }
        if (rejected != null && approved != null) {
            submitReviewInteractor.executeWithInput(SuggestionsModels.SubmitedReview(approved, rejected)) {
                onComplete { navigateBack() }
            }
        }
    }
}