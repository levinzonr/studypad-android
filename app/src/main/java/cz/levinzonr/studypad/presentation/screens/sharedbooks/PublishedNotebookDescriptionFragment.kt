package cz.levinzonr.studypad.presentation.screens.sharedbooks


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import cz.levinzonr.studypad.*

import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.models.State
import cz.levinzonr.studypad.presentation.adapters.NotePreviewAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_published_notebook_description.*
import kotlinx.android.synthetic.main.include_notebook_details.*
import kotlinx.android.synthetic.main.include_notebook_info.*
import kotlinx.android.synthetic.main.include_notebook_suggestions.*
import kotlinx.android.synthetic.main.include_notebook_version.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber
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

        viewModel.getSharedDetailLiveData().observe(viewLifecycleOwner, Observer {
            showDetail(it)
            showSugesstionsState(it.versionState)
        })

        viewModel.updated.observe(viewLifecycleOwner, Observer {
            it.handle { showToast("Done") }
        })

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            showVersionState(it)
        })
    }



    private fun showSugesstionsState(versionState: PublishedNotebook.VersionState) {
        notebookSuggestionShowAllBtn.setVisible(!versionState.modifications.isEmpty())
        notebookSuggestionShowAllBtn.setOnCloseIconClickListener {
            viewModel.onShowAllSuggestionsClicked()
        }
        notebookSuggestionsAddBtn.setOnCloseIconClickListener {
            viewModel.onCreateNewSuggestionClicked()
        }

        val message = if (versionState.modifications.isEmpty())
            "There are no pending suggestions yet. Have something to add?"
        else "There are ${versionState.modifications.count()} pending suggestion from N users"
        notebookSuggestionsMessage.text = message
    }


    private fun showDetail(detail: PublishedNotebook.Detail) {
        publishedNotebookNameTv.text = detail.title
        publishedNotebookAuthorTv.text = detail.author.displayName
        publishedBookAuthorIv.loadAuthorImage(detail.author.photoUrl)
        publishedBookTopicTv.text = detail.topic
        notebookDescriptionTv.text = detail.description
        detail.tags.map { Chip(context).apply { text = it } }.forEach {
            notebookTagsChips.addView(it)
        }

        publishedBookNotesRv.adapter = NotePreviewAdapter(detail.notes.first(3), this)
        publishedBookNotesRv.layoutManager = LinearLayoutManager(context)

        publishBookDateTv.text = "last updated: ${detail.lastUpdate.formatTime()}"
    }

    private fun preFillFeed(feed: PublishedNotebook.Feed) {
        publishedNotebookNameTv.text = feed.title
        publishedNotebookAuthorTv.text = feed.author.displayName
        publishedBookAuthorIv.loadAuthorImage(feed.author.photoUrl)
        publishedBookTopicTv.text = feed.topic
        publishedBookNotesRv.layoutManager = LinearLayoutManager(context)

        publishBookDateTv.text = "last updated: ${feed.lastUpdate.formatTime()}"
    }

    private fun showVersionState(state: State) {
        Timber.d("State: $state")
        when (state) {
            is State.UpdateAvailable -> {
                notebookVersionBtn.setIconResource(R.drawable.ic_sync_black_24dp)
                notebookVersionTv.text = "New version notebook is available! Tap to update"
                notebookVersionBtn.text = "Update"
                notebookVersionBtn.setOnClickListener { viewModel.handleSaveAction() }
                notebookVersionLayout.setVisible(true)

            }
            is State.SaveAvailable -> {
                notebookVersionBtn.setIconResource(R.drawable.ic_baseline_save_alt_24px)
                notebookVersionTv.text = "Doesn't look like you have this notebook yet"
                notebookVersionBtn.text = "Save"
                notebookVersionBtn.setOnClickListener { viewModel.handleSaveAction() }
                notebookVersionLayout.setVisible(true)

            }
            is State.MergeAvailable -> {
                notebookVersionBtn.setIconResource(R.drawable.ic_round_publish_24px)
                notebookVersionBtn.text = "Apply local changes"
                notebookVersionTv.text =
                    "Looks like you local notebook has divered from this one. Would you like apply your local changes?"
                notebookVersionBtn.setOnClickListener { viewModel.handleApplyChanges() }
                notebookVersionLayout.setVisible(true)

            }
            is State.UpToDate -> {
                notebookVersionLayout.setVisible(false)
            }
        }
    }

    override fun onShowAllButtonClicked() {
        viewModel.onShowAllNotesClicked()
    }

    companion object {
        private const val ARG_NOTEBOOK_ID = "notebookid"
        private const val ARG_FEED = "notebookfeed"

        fun newInstance(
            notebookId: String,
            feed: PublishedNotebook.Feed? = null
        ): PublishedNotebookDescriptionFragment {
            return PublishedNotebookDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NOTEBOOK_ID, notebookId)
                    putParcelable(ARG_FEED, feed)
                }
            }
        }
    }


}
