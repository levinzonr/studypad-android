package cz.levinzonr.studypad.presentation.screens.library.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.library.DeleteNoteInteractor
import cz.levinzonr.studypad.domain.interactors.library.PostNoteInteractor
import cz.levinzonr.studypad.domain.interactors.library.UpdateNoteInteractor
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.repository.NotesRepository
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import timber.log.Timber

class NoteDetailViewModel(
    private val viewMode: NoteDetailModels.NoteViewMode,
    private val repository: NotesRepository,
    private val updateNoteInteractor: UpdateNoteInteractor,
    private val deleteNoteInteractor: DeleteNoteInteractor,
    private val postNoteInteractor: PostNoteInteractor
) : BaseViewModel() {


    /* val currentMode : NoteDetailModels.NoteViewMode
         get() = viewMode*/

    var title: String
    var content: String

    val editModeLiveData = MutableLiveData<Boolean>()
    var noteLiveData: LiveData<Note>

    init {
        viewMode.let { viewMode ->
            when (viewMode) {
                is NoteDetailModels.NoteViewMode.Create -> {
                    editModeLiveData.postValue(true)
                    title = ""
                    content = ""
                    noteLiveData = MutableLiveData<Note>().apply {
                        postValue(Note(-1, "", "", viewMode.notebookId))
                    }
                }
                is NoteDetailModels.NoteViewMode.Edit -> {
                    title = viewMode.note.title ?: ""
                    content = viewMode.note.content ?: ""
                    editModeLiveData.postValue(false)
                    noteLiveData = repository.findById(viewMode.note.id)
                }
            }
        }
    }

    private fun createNote(notebookId: String) {
        postNoteInteractor.input = PostNoteInteractor.Input(notebookId, title, content)
        postNoteInteractor.execute {
            onComplete {
                navigateBack()
            }

            onError {
                handleApplicationError(it)
            }

        }
    }


    private fun editNote(note: Note) {
        note.let {
            updateNoteInteractor.input = UpdateNoteInteractor.Input(it.id, title, content)
            updateNoteInteractor.execute {
                onComplete {  editModeLiveData.postValue(false) }
                onError { handleApplicationError(it) }
            }
        }
    }

    fun deleteNote() {
        (viewMode as? NoteDetailModels.NoteViewMode.Edit)?.let { viewMode ->
            viewMode.note.let { note ->
                deleteNoteInteractor.executeWithInput(note.id) {
                    onComplete {
                        navigateBack()
                    }
                    onError {
                        handleApplicationError(it)
                    }
                }
            }
        }

    }


    fun handleModeChangeButton() {
        viewMode.let { viewMode ->
            when (viewMode) {
                is NoteDetailModels.NoteViewMode.Edit -> handleNoteUpdate(viewMode.note)
                is NoteDetailModels.NoteViewMode.Create -> {
                    if (title.isNotEmpty() || content.isNotEmpty())
                        createNote(viewMode.notebookId)
                    else {
                    }

                }
            }
        }


    }

    private fun handleNoteUpdate(note: Note) {
        val editMode = editModeLiveData.value ?: false
        val hasChanged = note.title != title || note.content != content
        Timber.d("Has changed: $hasChanged")
        when {
            editMode && hasChanged -> editNote(note)
            editMode && !hasChanged -> editModeLiveData.postValue(false)
            !editMode -> editModeLiveData.postValue(true)
        }
    }

    fun showEditNote() {
        //  navigateTo(NoteDetailFragmentDirections.actionNoteDetailFragmentToEditNoteFragment(note, null))
    }

}
