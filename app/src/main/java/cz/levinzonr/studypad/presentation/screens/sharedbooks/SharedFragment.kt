package cz.levinzonr.studypad.presentation.screens.sharedbooks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.presentation.adapters.PublishedNotebooksAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.common.VerticalSpaceItemDecoration
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.fragment_shared.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SharedFragment : BaseFragment(), PublishedNotebooksAdapter.PublishedNotebookItemListener {

    private val viewModel : ShareHubViewModel by viewModel()
    private val adapter: PublishedNotebooksAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shared, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        subscribe()
    }

    private fun subscribe() {
        viewModel.dataSource.observe(this, Observer {
            adapter.items = it
        })

        viewModel.loadingLiveData.observe(this, Observer {
            progressBar.setVisible(it)
        })

        viewModel.errorLiveData.observe(this, Observer {
            it.handle {
                showToast(it)
            }
        })
    }

    private fun setupRecyclerView() {
        sharedNotebooksRv.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(VerticalSpaceItemDecoration(20))
            adapter = this@SharedFragment.adapter
        }
        adapter.listener = this
    }

    override fun onPublishedNotebookClicked(publishedNotebook: PublishedNotebook.Feed) {

    }
}
