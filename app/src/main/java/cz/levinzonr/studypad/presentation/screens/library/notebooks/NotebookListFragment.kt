package cz.levinzonr.studypad.presentation.screens.library.notebooks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cz.levinzonr.studypad.BuildConfig

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.models.UserProfile
import cz.levinzonr.studypad.onHandle
import cz.levinzonr.studypad.presentation.adapters.NotebooksAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.common.VerticalSpaceItemDecoration
import cz.levinzonr.studypad.presentation.screens.showNotes
import cz.levinzonr.studypad.presentation.screens.showPublishNotebookView
import cz.levinzonr.studypad.presentation.screens.showPublishedNotebookDetail
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

        viewModel.syncCompletedEvent.onHandle(viewLifecycleOwner) {
            showToast("Library is Synchronized")
        }

        viewModel.notebookPublishedEven.onHandle(viewLifecycleOwner) {
             val link = "${BuildConfig.API_URL}/shared/${it.id}"
            shareMessage(link)
        }
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
        notebooksRv.addItemDecoration(VerticalSpaceItemDecoration(16))
        notebooksRv.adapter = adapter

    }

    override fun onNotebookSelected(notebook: Notebook) {
        showNotes(notebook)
    }

    override fun onNotebookMoreClicked(notebook: Notebook) {
        NotebookBottomMenuOptions.show(fragmentManager!!, notebook) {
            when (it) {
                R.id.notebookDeleteBtn -> viewModel.deleteNotebook(notebook)
                R.id.notebookEditBtn -> EditNotebookDialog.show(fragmentManager, notebook) { n, s ->
                    viewModel.updateNotebook(notebook, s)
                }
                R.id.notebookPublishBtn -> showPublishNotebookView(notebook)
                R.id.notebookOpenShared -> {
                    val mockFeed = PublishedNotebook.Feed("", "", 0, setOf(), 0, UserProfile("", "", "", null, null, false, ""), notebook.publishedNotebookId!!, "")
                    showPublishedNotebookDetail(mockFeed)
                }
                R.id.notebookShareBtn -> {
                    if (notebook.publishedNotebookId == null) {
                        viewModel.publishNotebook(notebook)
                    } else {
                        val link = "${BuildConfig.API_URL}/shared/${notebook.publishedNotebookId}"
                        shareMessage(link)
                    }
                }
            }
        }
    }

    private fun showNotebooks(list: List<Notebook>) {
        adapter.items = list
    }

}
