package cz.levinzonr.studypad.presentation.screens.library.notes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import cz.levinzonr.studypad.domain.Note
import cz.levinzonr.studypad.domain.interactors.GetNotesInteractor

class NotesListViewModel(
    private val notebookId: Long,
    private val getNotesInteractor: GetNotesInteractor
) : ViewModel() {

    val dataSource = MutableLiveData<List<Note>>()

    init {
        getNotesInteractor.input = GetNotesInteractor.Input(notebookId)
        getNotesInteractor.execute {
            onComplete { dataSource.postValue(it) }
        }
    }

}
