package cz.levinzonr.studypad.presentation.screens.library.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.base.BottomSheetOptionsDialog
import cz.levinzonr.studypad.presentation.base.NoteOptionsDialog
import cz.levinzonr.studypad.setVisible
import cz.levinzonr.studypad.supportActionBar
import kotlinx.android.synthetic.main.fragment_notes_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NotesListFragment : BaseFragment(), NotesAdapter.NotesItemListener {

    private val adapter: NotesAdapter by inject()
    private val args: NotesListFragmentArgs by navArgs()

    override val viewModel: NotesListViewModel by viewModel { parametersOf(args.NOTEBOOK.id) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes_list, container, false)
    }


    override fun subscribe() {
        viewModel.dataSource.observe(this, Observer {
            adapter.items  = it
            notesRv.setVisible(!it.isEmpty())
            emptyStateView.setVisible(it.isEmpty())
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        notesCreateNoteBtn.setOnClickListener {
            viewModel.showNoteCreation()
        }

        emptyStateView.configure(R.string.library_notes_empty_title, R.string.library_notes_empty_message)
    }

    override fun onStart() {
        super.onStart()
        supportActionBar?.subtitle = args.NOTEBOOK.name
    }

    override fun onStop() {
        super.onStop()
        supportActionBar?.subtitle = ""

    }

    private fun setupRecyclerView() {
        adapter.listener = this
        notesRv.layoutManager = LinearLayoutManager(context)
        notesRv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        notesRv.adapter = adapter
    }

    override fun onNoteClicked(note: Note) {
        viewModel.showNoteDetail(note)
    }

    override fun onNoteLongClicked(note: Note) {
        BottomSheetOptionsDialog.builder<NoteOptionsDialog>()
            .show(childFragmentManager) {
                when(it) {
                    R.id.noteCopy -> copyToClipboard("${note.title}\n${note.content}")
                    R.id.noteDelete -> viewModel.deleteNote(note)
                }
            }
    }
}
