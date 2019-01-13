package cz.levinzonr.studypad.presentation.screens.sharedbooks


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.presentation.adapters.CommentsAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.feedItem
import kotlinx.android.synthetic.main.fragment_publish_notebook.*
import kotlinx.android.synthetic.main.fragment_published_notebook_detail.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class PublishedNotebookDetailFragment : BaseFragment() {

    private val viewModel: PublishedNotebookDetailViewModel by viewModel { parametersOf(feedItem.id) }
    private val adapter: CommentsAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_published_notebook_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showInitial(feedItem)
        subscribe()
        setupRecyclerView()
    }

    private fun subscribe() {

        viewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {

        })

        viewModel.getDetailsLoadedEvent().observe(viewLifecycleOwner, Observer {
            it.handle {
                showDetail(it)
            }
        })

        viewModel.getCommentsLiveData().observe(viewLifecycleOwner, Observer {
            adapter.items = it
        })
    }

    private fun showInitial(feed: PublishedNotebook.Feed) {
        publishedBookNameTv.text = feed.title
        publishedBookDescriptionTv.text = feed.description
        publishedNotebookAuthorTv.text = feed.author.displayName
    }

    private fun showDetail(detail: PublishedNotebook.Detail) {
        publishedBookNameTv.text = detail.title
        publishedBookDescriptionTv.text = detail.description
        publishedBookTopicTv.text = "Topic"
        detail.tags.map { Chip(context).apply { text = it } }.forEach {
            publishedBookTagsCg.addView(it)
        }

    }

    private fun setupRecyclerView() {
        commentsRecyclerView.layoutManager = LinearLayoutManager(context)
        commentsRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        commentsRecyclerView.adapter = adapter
    }


}
