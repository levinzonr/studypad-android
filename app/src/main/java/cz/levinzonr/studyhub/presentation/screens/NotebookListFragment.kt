package cz.levinzonr.studyhub.presentation.screens

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import cz.levinzonr.studyhub.R
import cz.levinzonr.studyhub.domain.Notebook
import cz.levinzonr.studyhub.presentation.adapters.NotebooksAdapter
import cz.levinzonr.studyhub.presentation.base.BaseFragment
import cz.levinzonr.studyhub.presentation.showNotes
import kotlinx.android.synthetic.main.fragment_notebook_list.*

class NotebookListFragment : BaseFragment(), NotebooksAdapter.NotebookItemListener {

    companion object {
        fun newInstance() = NotebookListFragment()
    }

    private lateinit var viewModel: NotebookListViewModel
    private lateinit var adapter: NotebooksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notebook_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NotebookListViewModel::class.java)
        viewModel.dataSource.observe(this, Observer {
            showNotebooks(it)
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        adapter = NotebooksAdapter()
        adapter.listener = this
        notebooksRv.layoutManager = LinearLayoutManager(context)
        notebooksRv.adapter = adapter

    }

    override fun onNotebookSelected(notebook: Notebook) {
        showNotes(notebook.id)
    }

    private fun showNotebooks(list: List<Notebook>) {
        adapter.items = list
    }

}
