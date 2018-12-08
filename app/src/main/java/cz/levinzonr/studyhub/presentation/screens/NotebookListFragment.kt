package cz.levinzonr.studyhub.presentation.screens

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import cz.levinzonr.studyhub.R
import cz.levinzonr.studyhub.domain.Notebook
import cz.levinzonr.studyhub.presentation.adapters.NotebooksAdapter
import cz.levinzonr.studyhub.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.notebook_list_fragment.*

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
        return inflater.inflate(R.layout.notebook_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NotebookListViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dataSource.observe(this, Observer {
            showNotebooks(it)
        })
    }


    private fun setupRecyclerView() {
        adapter = NotebooksAdapter()
        adapter.listener = this
        notebooksRv.adapter = adapter

    }

    override fun onNotebookSelected(notebook: Notebook) {

    }

    private fun showNotebooks(list: List<Notebook>) {
        adapter.items = list
    }

}
