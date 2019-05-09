package cz.levinzonr.studypad.presentation.screens.library.notes

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.library.DeleteNoteInteractor
import cz.levinzonr.studypad.domain.interactors.library.GetNotesInteractor
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.repository.NotesRepository
import cz.levinzonr.studypad.presentation.base.BaseViewModel

class NotesListViewModel(
    private val notebookId: String,
    private val deleteNoteInteractor: DeleteNoteInteractor,
    private val getNotesInteractor: GetNotesInteractor,
    private val notesRepository: NotesRepository
) : BaseViewModel() {

    val dataSource = notesRepository.notesLiveData(notebookId)
    init {
        getNotesInteractor.executeWithInput(notebookId) {}
    }

    fun showNoteDetail(note: Note) {
        val editMode = NoteDetailModels.NoteViewMode.Edit(note)
        val dir = NotesListFragmentDirections.actionNotesListFragmentToNoteDetailFragment(editMode)
        navigateTo(dir)
    }

    fun showNoteCreation() {
        val createMode = NoteDetailModels.NoteViewMode.Create(notebookId)
        navigateTo(NotesListFragmentDirections.actionNotesListFragmentToNoteDetailFragment(createMode))
    }

    fun deleteNote(note: Note) {
        deleteNoteInteractor.executeWithInput(note.id) {
            onComplete {

            }
            onError {
                handleApplicationError(it)
            }
        }
    }

}
