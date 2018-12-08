package cz.levinzonr.studyhub.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import cz.levinzonr.studyhub.R


fun Fragment.showNotes(id: Long) {
    view?.findNavController()?.navigate(R.id.action_notebookListFragment_to_notesListFragment,
        Bundle().apply { putInt("NOTEBOOK_ID", id.toInt()) }
    )
}