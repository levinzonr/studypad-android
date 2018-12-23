package cz.levinzonr.studyhub.presentation.screens.library

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import cz.levinzonr.studyhub.domain.Note
import cz.levinzonr.studyhub.domain.interactors.GetNotesInteractor

class NotesListViewModel(
    private val getNotesInteractor: GetNotesInteractor
) : ViewModel() {
    val dataSource = MutableLiveData<List<Note>>()

    init {
        getNotesInteractor.execute {
            onComplete { dataSource.postValue(it) }
        }
    }

}
