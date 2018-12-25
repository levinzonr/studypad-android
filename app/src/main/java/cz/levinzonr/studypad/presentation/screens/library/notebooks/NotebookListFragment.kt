package cz.levinzonr.studypad.presentation.screens.library.notebooks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.presentation.adapters.NotebooksAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.showNotes
import kotlinx.android.synthetic.main.fragment_notebook_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotebookListFragment : BaseFragment(), NotebooksAdapter.NotebookItemListener {

    companion object {
        fun newInstance() = NotebookListFragment()
    }

    private val viewModel: NotebookListViewModel by viewModel()
    private val adapter: NotebooksAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notebook_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.dataSource.observe(this, Observer {
            showNotebooks(it)
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListeners()
    }

    private fun setupListeners() {
        notebooksAddFab.setOnClickListener {
            EditNotebookDialog.show(fragmentManager, null) { _, name: String ->
                viewModel.createNewNotebook(name)
            }

        }
    }

    private fun setupRecyclerView() {
        adapter.listener = this
        notebooksRv.layoutManager = LinearLayoutManager(context)
        notebooksRv.adapter = adapter

    }

    override fun onNotebookSelected(notebook: Notebook) {
       // showNotes(notebook)
       /* EditNotebookDialog.show(fragmentManager, notebook) {notebook, name ->
            notebook?.let { viewModel.updateNotebook(it, name) }
        }*/

        viewModel.deleteNotebook(notebook)
    }

    private fun showNotebooks(list: List<Notebook>) {
        adapter.items = list
    }

}