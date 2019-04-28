package cz.levinzonr.studypad.presentation.screens.sharinghub.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.presentation.screens.library.notes.NotesAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_published_notes_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PublishedNotesListFragment : BaseFragment(), NotesAdapter.NotesItemListener {

    private val args : PublishedNotesListFragmentArgs by navArgs()

    private val adapter: NotesAdapter by inject()

    override val viewModel: PublishedNotesListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_published_notes_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesRv.adapter = adapter
        notesRv.layoutManager = LinearLayoutManager(context)
        adapter.listener = this
        adapter.items = args.notes.asList()
    }


    override fun onNoteLongClicked(note: Note) {

    }

    override fun subscribe() {

    }

    override fun onNoteClicked(note: Note) {
        PublishedNoteDetailDialog.show(childFragmentManager, note)
    }

}
