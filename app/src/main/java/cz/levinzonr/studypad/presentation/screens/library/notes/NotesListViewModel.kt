package cz.levinzonr.studypad.presentation.screens.library.notes

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.library.GetNotesInteractor
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.repository.NotesRepository
import cz.levinzonr.studypad.presentation.base.BaseViewModel

class NotesListViewModel(
    private val notebookId: String,
    private val getNotesInteractor: GetNotesInteractor,
    private val notesRepository: NotesRepository
) : BaseViewModel() {

    val dataSource = notesRepository.notesLiveData(notebookId)

    init {
    }




    fun showNoteDetail(note: Note) {
        val dir = NotesListFragmentDirections.actionNotesListFragmentToNoteDetailFragment(note.id)
        navigateTo(dir)
    }

    fun showNoteCreation() {
        //navigateTo(NotesListFragmentDirections.actionNotesListFragmentToEditNoteFragment(null, notebookId))
    }

}
