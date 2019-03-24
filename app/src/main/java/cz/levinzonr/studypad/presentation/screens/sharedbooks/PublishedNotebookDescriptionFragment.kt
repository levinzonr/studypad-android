package cz.levinzonr.studypad.presentation.screens.sharedbooks


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.models.State
import cz.levinzonr.studypad.first
import cz.levinzonr.studypad.formatTime
import cz.levinzonr.studypad.loadAuthorImage
import cz.levinzonr.studypad.presentation.adapters.NotePreviewAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_published_notebook_description.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.security.InvalidParameterException


class PublishedNotebookDescriptionFragment : BaseFragment(), NotePreviewAdapter.NotePreviewListener {

    private val notebookId: String
        get() = arguments?.getString(ARG_NOTEBOOK_ID) ?: throw InvalidParameterException()


    override val viewModel: PublishedNotebookDetailViewModel by viewModel { parametersOf(notebookId) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_published_notebook_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<PublishedNotebook.Feed>(ARG_FEED)?.let(this::preFillFeed)

        scrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (scrollY > oldScrollY) saveButton.shrink(true)
            else saveButton.extend(true)
        }


        viewModel.getSharedDetailLiveData().observe(viewLifecycleOwner, Observer {
            showDetail(it)
        })

        viewModel.updated.observe(viewLifecycleOwner, Observer {
            it.handle { showToast("Done") }
        })

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is State.SaveAvailable -> {
                    saveButton.setIconResource(R.drawable.ic_baseline_save_alt_24px)
                    saveButton.setText("Save")
                    saveButton.setOnClickListener {
                        viewModel.handleSaveAction()
                    }
                }
                is State.UpdateAvailable -> {
                    saveButton.setIconResource(R.drawable.ic_sync_black_24dp)
                    saveButton.setText("Update")
                    saveButton.setOnClickListener {
                        viewModel.handleSaveAction()
                    }

                }
                is State.UpToDate -> {
                    saveButton.setIconResource(R.drawable.ic_check_black_24dp)
                    saveButton.setText("Up to date")
                    saveButton.setOnClickListener { showToast("Up to date!") }

                }
                is State.MergeAvailable -> {
                    saveButton.setIconResource(R.drawable.ic_round_publish_24px)
                    saveButton.setText("Apply changes")
                    saveButton.setOnClickListener { viewModel.handleApplyChanges() }
                }
            }
        })
    }


    private fun showDetail(detail: PublishedNotebook.Detail) {
        publishedNotebookNameTv.text = detail.title
        publishedNotebookAuthorTv.text = detail.author.displayName
        publishedBookAuthorIv.loadAuthorImage(detail.author.photoUrl)
        publishedBookTopicTv.text = "Subject: ${detail.topic}"
        publishedBookDescriptionTv.text = detail.description
        detail.tags.map { Chip(context).apply { text = it } }.forEach {
            publishedBookTagsCG.addView(it)
        }

        publishedBookNotesRv.adapter = NotePreviewAdapter(detail.notes.first(3), this)
        publishedBookNotesRv.layoutManager = LinearLayoutManager(context)

        publishBookDateTv.text = "last updated: ${detail.lastUpdate.formatTime()}"
    }

    private fun preFillFeed(feed: PublishedNotebook.Feed) {
        publishedNotebookNameTv.text = feed.title
        publishedNotebookAuthorTv.text = feed.author.displayName
        publishedBookAuthorIv.loadAuthorImage(feed.author.photoUrl)
        publishedBookTopicTv.text = "Subject: ${feed.topic}"
        publishedBookDescriptionTv.text = feed.description


        publishedBookNotesRv.layoutManager = LinearLayoutManager(context)

        publishBookDateTv.text = "last updated: ${feed.lastUpdate.formatTime()}"
    }

    override fun onShowAllButtonClicked() {

    }

    companion object {
        private const val ARG_NOTEBOOK_ID = "notebookid"
        private const val ARG_FEED = "notebookfeed"

        fun newInstance(notebookId: String, feed: PublishedNotebook.Feed? = null): PublishedNotebookDescriptionFragment {
            return PublishedNotebookDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NOTEBOOK_ID, notebookId)
                    putParcelable(ARG_FEED, feed)
                }
            }
        }
    }


}
