package cz.levinzonr.studypad.presentation.screens.selectors.language

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.GetAvailableLanguagesInteractor
import cz.levinzonr.studypad.domain.models.Locale
import cz.levinzonr.studypad.presentation.base.BaseViewModel

class LanguageSelectorViewModel(private val getAvailableLanguagesInteractor: GetAvailableLanguagesInteractor) : BaseViewModel() {

    private val _languagesLiveData: MutableLiveData<List<Locale>> = MutableLiveData()

    val languageLiveData: LiveData<List<Locale>>
        get() = _languagesLiveData

    init {
        getAvailableLanguagesInteractor.execute {
            onComplete { _languagesLiveData.postValue(it) }
        }
    }




}