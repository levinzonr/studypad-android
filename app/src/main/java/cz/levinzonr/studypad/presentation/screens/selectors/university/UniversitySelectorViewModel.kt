package cz.levinzonr.studypad.presentation.screens.selectors.university

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.GetUniversitiesInteractor
import cz.levinzonr.studypad.domain.interactors.SuggestUniversityInteractor
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.Event
import timber.log.Timber

class UniversitySelectorViewModel(
    private val suggestUniversityInteractor: SuggestUniversityInteractor,
    private val getUniversitiesInteractor: GetUniversitiesInteractor
) : BaseViewModel() {

    val stateLiveData = MutableLiveData<UniversitySelectorViewState>()
    val universitySelectedEvent = MutableLiveData<Event<University>?>(

    )

    init {
        stateLiveData.postValue(UniversitySelectorViewState())
        universitySelectedEvent.postValue(null)
    }




    fun findUnversities(query: String) {
        Timber.d("Fimd: $query")
        getUniversitiesInteractor.unsubscribe()
        if (query.isBlank()) {
            Timber.d("Fim black")
            toggleLoading(false)
            stateLiveData.postValue(stateLiveData.value?.copy(empty = true, universities = listOf()))
        } else {
            Timber.d("FRin")
            toggleLoading(true)
            getUniversitiesInteractor.executeWithInput(query) {
                onComplete {
                    Timber.d("FiDoen")
                    toggleLoading(false)
                    stateLiveData.postValue(stateLiveData.value?.copy(universities = it, empty = false))
                }
                onError {
                    Timber.d("$it")
                    handleApplicationError(it)
                }
                onCancel {
                    Timber.d("cancel $query")
                }
            }
        }
    }


    fun suggestionUniversity(input: String) {
        suggestUniversityInteractor.executeWithInput(input) {
            onComplete { onUniversitySelected(it) }
            onError { handleApplicationError(it) }
        }
    }

    fun onUniversitySelected(university: University) {
        universitySelectedEvent.postValue(Event(university))
        navigateBack()
    }


}