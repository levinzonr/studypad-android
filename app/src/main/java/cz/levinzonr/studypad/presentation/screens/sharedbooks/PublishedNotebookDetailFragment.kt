package cz.levinzonr.studypad.presentation.screens.sharedbooks


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.chip.Chip

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.feedItem
import kotlinx.android.synthetic.main.fragment_publish_notebook.*
import kotlinx.android.synthetic.main.fragment_published_notebook_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class PublishedNotebookDetailFragment : BaseFragment() {

    private val viewModel: PublishedNotebookDetailViewModel by viewModel { parametersOf(feedItem.id) }

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
    }

    private fun subscribe() {

        viewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {

        })

        viewModel.getDetailsLoadedEvent().observe(viewLifecycleOwner, Observer {
            it.handle {
                showDetail(it)
            }
        })
    }

    private fun showInitial(feed: PublishedNotebook.Feed) {
        publishedBookNameTv.text = feed.title
        publishedBookDescriptionTv.text = feed.description
    }

    private fun showDetail(detail: PublishedNotebook.Detail) {
        publishedBookNameTv.text = detail.title
        publishedBookDescriptionTv.text = detail.description
        publishedBookTopicTv.text = "Topic"
        detail.tags.map { Chip(context).apply { text = it } }.forEach {
            publishedBookTagsCg.addView(it)
        }

    }


}
