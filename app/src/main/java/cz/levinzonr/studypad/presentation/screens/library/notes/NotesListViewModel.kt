package cz.levinzonr.studypad.presentation.screens.library.notes

import cz.levinzonr.studypad.domain.interactors.library.GetNotesInteractor
import cz.levinzonr.studypad.domain.repository.NotesRepository
import cz.levinzonr.studypad.presentation.base.BaseViewModel

class NotesListViewModel(
    private val notebookId: String,
    private val getNotesInteractor: GetNotesInteractor,
    private val notesRepository: NotesRepository
) : BaseViewModel() {

    val dataSource = notesRepository.notesLiveData(notebookId)

    init {
        getNotesInteractor.executeWithInput(notebookId) {

        }
    }

}
