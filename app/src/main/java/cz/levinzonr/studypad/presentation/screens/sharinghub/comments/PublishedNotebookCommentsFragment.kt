package cz.levinzonr.studypad.presentation.screens.sharinghub.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.messaging.FirebaseMessaging
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_published_notebook_comments.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber
import java.security.InvalidParameterException
import cz.levinzonr.studypad.baseActivity
import cz.levinzonr.studypad.notifications.NotificationPayload
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.presentation.base.BackButtonHandler
import cz.levinzonr.studypad.presentation.base.BottomSheetOptionsDialog
import cz.levinzonr.studypad.presentation.base.NotificationHandler
import cz.levinzonr.studypad.presentation.screens.notifications.NotificationType


class PublishedNotebookCommentsFragment : BaseFragment(), CommentsAdapter.CommentsItemListener, BackButtonHandler, NotificationHandler {

    private val notebookId: String
        get() = arguments?.getString(ARG_NOTEBOOK_ID) ?: throw InvalidParameterException()

    private val userManager: UserManager by inject()

    override val viewModel: PublishedNotebookCommentsViewModel by viewModel { parametersOf(notebookId) }
    private val adapter : CommentsAdapter by inject { parametersOf(this, userManager.getCurrentUserInfo()?.id) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_published_notebook_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupRecyclerView()
    }

    override fun subscribe() {

        viewModel.getCommentsLiveData().observeNonNull(viewLifecycleOwner) {
            Timber.d("submti $it")
            adapter.submitList(it)
            commentsRecyclerView.smoothScrollToPosition(0)
        }

    }

    override fun onStart() {
        super.onStart()
        FirebaseMessaging.getInstance().subscribeToTopic("comments_$notebookId")
    }

    override fun onStop() {
        super.onStop()
        FirebaseMessaging.getInstance().unsubscribeFromTopic("comments_$notebookId")
    }

    override fun onLoseFocus() {
        Timber.d("onLocse")
        commentEditText?.hideKeyboard()
    }

    private fun setupListeners() {
        commentEditText.listener = object :
            CommentEditText.CommentEditTextListener {
            override fun onSendButtonClicked(text: String) {
                viewModel.createComment(text)
            }

            override fun onChangeConfirmButtonClicked(comment: PublishedNotebook.Comment, text: String) {
                viewModel.editComment(comment, text)
            }
        }
    }

    private fun setupRecyclerView() {
        commentsRecyclerView.layoutManager = LinearLayoutManager(context)
        commentsRecyclerView.adapter = adapter
    }



    override fun onCommentMoreButtonPressed(comment: PublishedNotebook.Comment) {
        BottomSheetOptionsDialog.builder<CommentOptionsMenu>()
            .show(childFragmentManager) {
            Timber.d("onSelected")
            when (it) {
                R.id.commentEdit -> {
                    Timber.d("edit")
                    commentEditText.editComment(comment)
                }
                R.id.commentDelete -> {
                    viewModel.deleteComment(comment)
                }
            }

        }
    }

    override fun handleNotification(type: NotificationType, notificationPayload: NotificationPayload) {
        Timber.d("Hande ")
        viewModel.loadComments()
    }

    override fun handleBackButton() {
        commentEditText?.let {
            if (it.editModeActive) {
                it.clear()
            } else {
                baseActivity?.navigateBack()
            }
        }

    }

    companion object {

        private const val ARG_NOTEBOOK_ID = "notebookid"
        fun newInstance(notebookId: String): PublishedNotebookCommentsFragment {
            return PublishedNotebookCommentsFragment().apply {
                arguments = Bundle().apply { putString(ARG_NOTEBOOK_ID, notebookId) }
            }
        }
    }
}
