package cz.levinzonr.studypad.presentation.screens.library.notes

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.call
import cz.levinzonr.studypad.domain.interactors.library.DeleteNoteInteractor
import cz.levinzonr.studypad.domain.interactors.library.PostNoteInteractor
import cz.levinzonr.studypad.domain.interactors.library.UpdateNoteInteractor
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.repository.NotesRepository
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.SimpleEvent

class NoteDetailViewModel(
    private val noteId: Long,
    private val repository: NotesRepository,
    private val updateNoteInteractor: UpdateNoteInteractor,
    private val deleteNoteInteractor: DeleteNoteInteractor,
    private val postNoteInteractor: PostNoteInteractor
) : BaseViewModel() {

    val noteLiveData = repository.findById(noteId)
    private val note: Note?
        get() = noteLiveData.value

    var title: String = noteLiveData.value?.title ?: ""
    var content: String = noteLiveData.value?.content ?: ""

    val noteCreatedEvent: MutableLiveData<SimpleEvent> = MutableLiveData()
    val noteDeletedEvent: MutableLiveData<SimpleEvent> = MutableLiveData()
    val editModeLiveData = MutableLiveData<Boolean>()


    init {
        editModeLiveData.postValue(false)
    }

    fun createNote(notebookId: String) {
        postNoteInteractor.input = PostNoteInteractor.Input(notebookId, title, content)
        postNoteInteractor.execute {
            navigateBack()
        }
    }



    fun editNote() {
        note?.let {
            updateNoteInteractor.input = UpdateNoteInteractor.Input(it.id, title, content)
            updateNoteInteractor.execute {
                editModeLiveData.postValue(false)
            }
        }
    }

    fun deleteNote() {
        note?.let { note ->
            deleteNoteInteractor.executeWithInput(note.id) {
                onComplete { noteDeletedEvent.call() }
            }
        }
    }


    fun handleModeChangeButton() {
        val editMode = editModeLiveData.value ?: false
        if (editMode) {
            editNote()
        } else {
            editModeLiveData.postValue(!editMode)
        }
    }

    fun showEditNote() {
        //  navigateTo(NoteDetailFragmentDirections.actionNoteDetailFragmentToEditNoteFragment(note, null))
    }

}
