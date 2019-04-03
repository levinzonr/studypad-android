package cz.levinzonr.studypad.presentation.screens.library.notes

import cz.levinzonr.studypad.domain.models.Note
import java.io.Serializable

object NoteDetailModels {


    sealed class NoteViewMode : Serializable {
        data class Create(val notebookId: String) : NoteViewMode()
        data class Edit(val note: Note) : NoteViewMode()
    }


    data class NoteViewState(
        val editModeActive: Boolean,
        val hasChanged:  Boolean,
        val title: String,
        val content: String)

}