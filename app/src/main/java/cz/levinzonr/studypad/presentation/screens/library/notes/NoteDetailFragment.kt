package cz.levinzonr.studypad.presentation.screens.library.notes

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.baseActivity
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.note
import cz.levinzonr.studypad.presentation.screens.showNoteEdit
import kotlinx.android.synthetic.main.note_detail_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class NoteDetailFragment : BaseFragment() {

    companion object {
        fun newInstance() = NoteDetailFragment()
    }

    private val viewModel: NoteDetailViewModel by sharedViewModel { parametersOf(note) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.note_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteDetailEditFab.setOnClickListener {
            showNoteEdit(note.id, note)
        }
    }

}
