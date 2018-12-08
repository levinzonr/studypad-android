package cz.levinzonr.studyhub.presentation.screens

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import cz.levinzonr.studyhub.R
import cz.levinzonr.studyhub.domain.Note
import cz.levinzonr.studyhub.presentation.adapters.NotesAdapter
import cz.levinzonr.studyhub.presentation.base.BaseFragment
import cz.levinzonr.studyhub.presentation.showNoteEdit
import kotlinx.android.synthetic.main.fragment_notes_list.*

class NotesListFragment : BaseFragment(), NotesAdapter.NotesItemListener {

    private lateinit var adapter: NotesAdapter

    companion object {
        fun newInstance() = NotesListFragment()
    }

    private lateinit var viewModel: NotesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NotesListViewModel::class.java)
        setupRecyclerView()
        viewModel.dataSource.observe(this, Observer {
            adapter.items  = it
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        adapter = NotesAdapter()
        adapter.listener = this
        notesRv.layoutManager = LinearLayoutManager(context)
        notesRv.adapter = adapter
    }

    override fun onNoteSelected(note: Note) {
        showNoteEdit(note)
    }
}
