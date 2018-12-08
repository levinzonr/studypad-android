package cz.levinzonr.studyhub.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import cz.levinzonr.studyhub.R
import cz.levinzonr.studyhub.domain.Note


fun Fragment.showNotes(id: Long) {
    view?.findNavController()?.navigate(R.id.action_notebookListFragment_to_notesListFragment,
        Bundle().apply { putInt("NOTEBOOK_ID", id.toInt()) }
    )
}

fun Fragment.showNoteEdit(note: Note?) {
    view?.findNavController()?.navigate(R.id.action_notesListFragment_to_editNoteFragment,
        Bundle().apply { putParcelable("NOTE", note) })
}