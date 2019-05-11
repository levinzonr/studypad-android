package cz.levinzonr.studypad.presentation.screens.sharinghub.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cz.levinzonr.studypad.*
import cz.levinzonr.studypad.domain.models.Note

import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.models.State
import cz.levinzonr.studypad.notifications.NotificationPayload
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.base.NotificationHandler
import cz.levinzonr.studypad.presentation.screens.notifications.NotificationType
import cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.SuggestionsInfoDialog
import kotlinx.android.synthetic.main.fragment_published_notebook_description.*
import kotlinx.android.synthetic.main.include_notebook_details.*
import kotlinx.android.synthetic.main.include_notebook_excluded_view.*
import kotlinx.android.synthetic.main.include_notebook_info.*
import kotlinx.android.synthetic.main.include_notebook_suggestions.*
import kotlinx.android.synthetic.main.include_notebook_version.*
import kotlinx.android.synthetic.main.inlcude_published_actions.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber
import java.security.InvalidParameterException


class PublishedNotebookDescriptionFragment : BaseFragment(), NotePreviewAdapter.NotePreviewListener, NotificationHandler {

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
        emptyView.setActionButton {
            viewModel.refreshAll()
        }

        publishedShareBtn.setOnClickListener {
            shareMessage("", notebookId.toNotebookLink())
        }
        publishedCopyLinkBtn.setOnClickListener {
            copyToClipboard(notebookId.toNotebookLink())
        }

        publishedImportCopyBtn.setOnClickListener {
            viewModel.onImportCopyButtonClicked()
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshAll()
    }

    private fun showSugesstionsState(versionState: PublishedNotebook.VersionState) {

        val unigueUsers = versionState.modifications.map { it.author }.distinctBy { it.uuid }.count()

        notebookSuggestionShowAllBtn.setVisible(!versionState.modifications.isEmpty())
        notebookSuggestionShowAllBtn.setOnClickListener {
            viewModel.onShowAllSuggestionsClicked()
        }

        if (versionState.modifications.count() == 0) {
            notebookSuggestionsMessage.text = getString(R.string.sharinghub_dets_syggestions_empty)

        } else {
            notebookSuggestionsMessage.text = getQuantatyString(R.plurals.sharinghub_dets_suggestions_message, versionState.modifications.count())

        }
        notebookSuggestionsLayout.setVisible(true)

    }

    override fun subscribe() {

        arguments?.getParcelable<PublishedNotebook.Feed>(ARG_FEED)?.let(this::preFillFeed)

        viewModel.getSharedDetailLiveData().observe(viewLifecycleOwner, Observer {
            showDetail(it)
            showSugesstionsState(it.versionState)
        })

        viewModel.updated.observe(viewLifecycleOwner, Observer {
           it?.handle { showToast(it) }
        })

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            showVersionState(it)
        })

    }

    private fun showDetail(detail: PublishedNotebook.Detail) {
        emptyView.setVisible(false)
        publishedNotebookNameTv.text = detail.title
        publishedNotebookAuthorTv.text = detail.author.displayName
        publishedBookAuthorIv.loadAuthorImage(detail.author.photoUrl)
        publishedBookTopicTv.shownText = detail.topic
        publishedBookLanguageTv.shownText = detail.languageCode
        publishedBookSchoolTv.shownText = detail.university?.fullName


        publishedBookNotesRv.adapter =
            NotePreviewAdapter(detail.notes, this)
        publishedBookNotesRv.layoutManager = LinearLayoutManager(context)

        publishBookDateTv.text = "last updated: ${detail.lastUpdate.formatTime(context!!)}"
        notebookInfoLayout.setVisible(true)
        notesPreviewLayout.setVisible(true)

        notebookExcludedLayout.setVisible(detail.excludedFromSearch && detail.authoredByMe)

        notebookPublishBtn.setOnClickListener {
            viewModel.onUpdateNotebookClicked()
        }

        showDecsription(detail.description ?: "", detail.tags.toList())
        notebookInfoActionsLayout.setVisible(true)

    }

    private fun showDecsription(description: String, tags: List<String>) {
        notebookDescriptionTv.shownText = description
        notebookTagsCg.removeAllViews()
        tags.buildTags(context!!).forEach(notebookTagsCg::addView)
        notebookDescriptionLayout.setVisible(description.isNotEmpty() || tags.isNotEmpty())
        descriptionTitleTv.setVisible(description.isNotEmpty())
        descriptionTagsCG.setVisible(tags.isNotEmpty())
    }

    private fun preFillFeed(feed: PublishedNotebook.Feed) {

        publishedNotebookNameTv.text = feed.title
        publishedNotebookAuthorTv.text = feed.author.displayName
        publishedBookAuthorIv.loadAuthorImage(feed.author.photoUrl)
        publishedBookTopicTv.text = feed.topic
        publishedBookNotesRv.layoutManager = LinearLayoutManager(context)

        publishBookDateTv.text = getString(R.string.sharinghub_dets_lastupdated,  "${feed.lastUpdate.formatTime(context!!)}")
        publishedBookSchoolTv.shownText = feed.university?.fullName

        notebookInfoLayout.setVisible(true)

    }

    private fun showVersionState(state: State) {
        Timber.d("State: $state")
        when (state) {
            is State.UpdateAvailable -> {
                notebookVersionBtn.setIconResource(R.drawable.ic_sync_black_24dp)
                notebookVersionTv.setText(R.string.sharinghub_version_update_message)
                notebookVersionBtn.setText(R.string.sharinghub_version_update_action)
                notebookVersionBtn.setOnClickListener { viewModel.handleSaveAction() }
                notebookVersionLayout.setVisible(true)

            }
            is State.SaveAvailable -> {
                notebookVersionBtn.setIconResource(R.drawable.ic_baseline_save_alt_24px)
                notebookVersionTv.setText(R.string.sharinghub_version_none_message)
                notebookVersionBtn.setText(R.string.sharinghub_version_none_action)
                notebookVersionBtn.setOnClickListener { viewModel.handleSaveAction() }
                notebookVersionLayout.setVisible(true)

            }
            is State.MergeAvailable -> {
                notebookVersionBtn.setIconResource(R.drawable.ic_round_publish_24px)
                notebookVersionBtn.text = if (state.authoredByMe) getString(R.string.sharinghub_version_modify_action) else getString(R.string.sharinghub_version_suggest_action)
                notebookVersionTv.text = if (state.authoredByMe) getString(R.string.sharinghub_version_modify_message) else getString(R.string.sharinghub_version_suggest_message)
                notebookVersionBtn.setOnClickListener { viewModel.handleApplyChanges() }
                notebookVersionLayout.setVisible(true)

            }
            is State.UpToDate -> {
                notebookVersionLayout.setVisible(false)
            }
        }
    }

    override fun showLoading(isLoading: Boolean) {
        Timber.d("Show Loading :$isLoading")
        progressBar.setVisible(isLoading)
    }


    override fun showNetworkUnavailableError() {
        super.showNetworkUnavailableError()
        emptyView.configureAsNetworkError()
        emptyView.setVisible(true)
    }

    override fun handleNotification(type: NotificationType, notificationPayload: NotificationPayload) {
        viewModel.refreshAll()
    }

    override fun onShowAllButtonClicked() {
        viewModel.onShowAllNotesClicked()
    }

    override fun onNotePreviewClicked(note: Note) {
        PublishedNoteDetailDialog.show(childFragmentManager, note)
    }

    companion object {
        private const val ARG_NOTEBOOK_ID = "notebookid"
        private const val ARG_FEED = "notebookfeed"

        fun newInstance(
            notebookId: String,
            feed: PublishedNotebook.Feed? = null
        ): PublishedNotebookDescriptionFragment {
            return PublishedNotebookDescriptionFragment()
                .apply {
                arguments = Bundle().apply {
                    putString(ARG_NOTEBOOK_ID, notebookId)
                    putParcelable(ARG_FEED, feed)
                }
            }
        }
    }


}
