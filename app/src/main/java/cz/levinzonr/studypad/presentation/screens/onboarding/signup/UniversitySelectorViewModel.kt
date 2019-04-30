package cz.levinzonr.studypad.presentation.screens.onboarding.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.call
import cz.levinzonr.studypad.domain.interactors.GetUniversitiesInteractor
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.Event

class UniversitySelectorViewModel(private val getUniversitiesInteractor: GetUniversitiesInteractor) : BaseViewModel() {

    val stateLiveData = MutableLiveData<UniversitySelectorViewState>()
    init {
        stateLiveData.postValue(UniversitySelectorViewState())
    }



    fun findUnversities(query: String) {
        if (query.isBlank()) {
            stateLiveData.postValue(stateLiveData.value?.copy(empty = true, universities = listOf()))
        } else {

            toggleLoading(true)
            getUniversitiesInteractor.unsubscribe()
            getUniversitiesInteractor.executeWithInput(query) {
                onComplete {
                    toggleLoading(false)
                    stateLiveData.postValue(stateLiveData.value?.copy(universities = it, empty = false))
                }
                onError {

                }
            }
        }
    }


}