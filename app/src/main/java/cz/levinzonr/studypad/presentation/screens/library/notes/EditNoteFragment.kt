package cz.levinzonr.studypad.presentation.screens.library.notes


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.onTextChanged
import cz.levinzonr.studypad.presentation.base.BackButtonHandler
import cz.levinzonr.studypad.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_edit_note.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class EditNoteFragment : BaseFragment(), BackButtonHandler{


   // private val args: EditNoteFragmentArgs by navArgs()

    private val note: Note?
        get() = null

    private val notebookId: String?
        get() =null

    override val viewModel: NoteDetailViewModel by viewModel { parametersOf(note) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_note, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        noteContentEt.onTextChanged {
            viewModel.content = it
        }

        noteTitleEt.onTextChanged {
            viewModel.title = it
        }


    }

    override fun onStart() {
        super.onStart()
        with(viewModel) {
            noteTitleEt.setText(title)
            noteContentEt.setText(content)
        }
    }

    override fun handleBackButton() {
        if (note == null) viewModel.createNote(notebookId!!)
        else viewModel.editNote()
    }
}
