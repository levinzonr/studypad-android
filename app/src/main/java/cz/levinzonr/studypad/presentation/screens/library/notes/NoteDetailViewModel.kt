package cz.levinzonr.studypad.presentation.screens.library.notes

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.call
import cz.levinzonr.studypad.domain.interactors.library.DeleteNoteInteractor
import cz.levinzonr.studypad.domain.interactors.library.PostNoteInteractor
import cz.levinzonr.studypad.domain.interactors.library.UpdateNoteInteractor
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.events.SimpleEvent

class NoteDetailViewModel(
    private val note: Note?,
    private val updateNoteInteractor: UpdateNoteInteractor,
    private val deleteNoteInteractor: DeleteNoteInteractor,
    private val postNoteInteractor: PostNoteInteractor
): BaseViewModel() {

    var title: String = note?.title ?: ""
    var content: String = note?.content ?: ""

    val noteCreatedEvent : MutableLiveData<SimpleEvent> = MutableLiveData()
    val noteDeletedEvent : MutableLiveData<SimpleEvent> = MutableLiveData()

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
                onComplete { noteCreatedEvent.call() }
            }
        }
    }

    fun deleteNote() {
        note ?: return
        deleteNoteInteractor.executeWithInput(note.id) {
            onComplete { noteDeletedEvent.call() }
        }
    }


    fun showEditNote() {
      //  navigateTo(NoteDetailFragmentDirections.actionNoteDetailFragmentToEditNoteFragment(note, null))
    }

}
