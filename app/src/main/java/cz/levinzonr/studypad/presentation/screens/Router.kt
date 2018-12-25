package cz.levinzonr.studypad.presentation.screens

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.presentation.screens.library.notes.EditNoteFragment
import cz.levinzonr.studypad.presentation.screens.library.notes.NoteDetailFragment
import cz.levinzonr.studypad.presentation.screens.library.notes.NotesListFragment

private const val ARG_NOTEBOOK = "NOTEBOOK"
private const val ARG_NOTEBOOK_ID = "NOTEBOOK_ID"
private const val ARG_NOTE = "NOTE"

fun Fragment.showNotes(notebook: Notebook) {
    view?.findNavController()?.navigate(R.id.action_notebookListFragment_to_notesListFragment,
        Bundle().apply { putParcelable(ARG_NOTEBOOK, notebook) }
    )
}

fun NotesListFragment.showNoteEdit(notebookId: Long, note: Note?) {
    view?.findNavController()?.navigate(R.id.action_notesListFragment_to_editNoteFragment,
        Bundle().apply {
            putParcelable(ARG_NOTE, note)
            putLong(ARG_NOTEBOOK_ID, notebookId)
        })
}

fun NoteDetailFragment.showNoteEdit(notebookId: Long, note: Note?) {
    view?.findNavController()?.navigate(R.id.action_noteDetailFragment_to_editNoteFragment,
        Bundle().apply {
            putParcelable(ARG_NOTE, note)
            putLong(ARG_NOTEBOOK_ID, notebookId)
        })
}


fun NotesListFragment.showNoteDetail(note: Note) {
    view?.findNavController()?.navigate(R.id.action_notesListFragment_to_noteDetailFragment,
        Bundle().apply { putParcelable(ARG_NOTE, note) })
}

val NotesListFragment.notebook: Notebook?
    get() =  arguments?.getParcelable(ARG_NOTEBOOK)

val EditNoteFragment.note: Note?
    get() = arguments?.getParcelable(ARG_NOTE)

val NoteDetailFragment.note: Note
    get() = arguments?.getParcelable(ARG_NOTE)!!


val EditNoteFragment.notebookId: Long
    get() = arguments?.getLong(ARG_NOTEBOOK_ID)!!

fun Fragment.showMain() {
    startActivity(Intent(activity, MainActivity::class.java))
    activity?.finish()
}

fun Fragment.showAccountCreation() {
    view?.findNavController()?.navigate(R.id.action_loginFragment2_to_accountInfoFragment)
}

fun Fragment.showAccounCreationNextStep() {
    view?.findNavController()?.navigate(R.id.action_accountInfoFragment_to_credentialsInfoFragment)
}

fun Fragment.showUniversitySelector() {
    view?.findNavController()?.navigate(R.id.action_accountInfoFragment_to_universitySelectorFragment)
}

fun Fragment.navigateBack() {
    view?.findNavController()?.popBackStack()
}


