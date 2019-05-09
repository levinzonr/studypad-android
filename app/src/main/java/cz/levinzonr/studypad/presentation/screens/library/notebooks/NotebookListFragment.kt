package cz.levinzonr.studypad.presentation.screens.library.notebooks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cz.levinzonr.studypad.*

import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.common.ToolbarSpaceDecoration
import cz.levinzonr.studypad.presentation.common.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_notebook_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class NotebookListFragment : BaseFragment(), NotebooksAdapter.NotebookItemListener {


    override val viewModel: NotebookListViewModel by sharedViewModel()
    private val adapter: NotebooksAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notebook_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListeners()

        emptyStateView.configure(
            R.string.library_notebooks_empty_title, R.string.library_notebooks_empty_message)
        emptyStateView.setActionButton(R.string.library_notebooks_empty_action) {
            findNavController().navigate(R.id.sharingHubFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshNotebooks()
    }

    private fun setupListeners() {
        notebooksAddFab.setOnClickListener {
            EditNotebookDialog.show(fragmentManager, null) { _, name: String ->
                viewModel.createNewNotebook(name)
            }

        }
    }

    override fun showLoading(isLoading: Boolean) {
        progressBar.setVisible(isLoading)
    }
    private fun setupRecyclerView() {
        adapter.listener = this
        notebooksRv.layoutManager = LinearLayoutManager(context)
        notebooksRv.addItemDecoration(VerticalSpaceItemDecoration(16))
        notebooksRv.addItemDecoration(ToolbarSpaceDecoration())
        notebooksRv.adapter = adapter

    }


    override fun subscribe() {
        viewModel.dataSource.observeNonNull(viewLifecycleOwner) {
            showNotebooks(it)
        }


        viewModel.notebookPublishedEven.onHandle(viewLifecycleOwner) {
            val link = it.id.toNotebookLink()
            shareMessage(it.title, link)
        }

    }
    override fun onNotebookSelected(notebook: Notebook) {
        viewModel.onNotebookSelected(notebook)
    }

    override fun onNotebookMoreClicked(notebook: Notebook) {
        NotebookBottomMenuOptions.show(fragmentManager!!, notebook) {
            when (it) {
                R.id.notebookDeleteBtn -> viewModel.deleteNotebook(notebook)
                R.id.notebookEditBtn -> EditNotebookDialog.show(fragmentManager, notebook) { n, s ->
                    viewModel.updateNotebook(notebook, s)
                }
                R.id.notebookPublishBtn -> viewModel.startPublishNotebookFlow(notebook)
                R.id.notebookOpenShared -> {
                    Timber.d("Hello")
                    notebook.publishedNotebookId?.let {
                        Timber.d(it)
                        viewModel.onShowPublishedNotebook(it)
                    }
                }
                R.id.notebookShareBtn -> {
                    if (notebook.publishedNotebookId == null) {
                        viewModel.publishNotebook(notebook)
                    } else {
                        val link = notebook.publishedNotebookId.toNotebookLink()
                        shareMessage(notebook.name, link)
                    }
                }
                R.id.notebookCopyBtn -> {
                    if (notebook.publishedNotebookId != null) {
                        val link = notebook.publishedNotebookId.toNotebookLink()
                        copyToClipboard(link)
                    }
                }
            }
        }
    }

    private fun showNotebooks(list: List<Notebook>) {
        adapter.submitList(list)
        emptyStateView.setVisible(list.isEmpty())
        notebooksRv.setVisible(!list.isEmpty())
    }

}
