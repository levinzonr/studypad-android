package cz.levinzonr.studypad.presentation.screens.library.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.note_detail_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NoteDetailFragment : BaseFragment() {


    //private val args: NoteDetailFragmentArgs by navArgs()


    override val viewModel: NoteDetailViewModel by viewModel { parametersOf(Note(1, notebookId = "")) }

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
           viewModel.showEditNote()
        }

        //noteDetailView.setNoteDetails(args.note)
    }

}
