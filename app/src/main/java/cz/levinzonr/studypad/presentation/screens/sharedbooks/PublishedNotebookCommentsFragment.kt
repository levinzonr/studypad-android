package cz.levinzonr.studypad.presentation.screens.sharedbooks

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.onHandle
import cz.levinzonr.studypad.onTextChanged
import cz.levinzonr.studypad.presentation.adapters.CommentsAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_published_notebook_comments.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber
import java.security.InvalidParameterException
import android.app.Activity
import androidx.core.content.ContextCompat.getSystemService
import android.view.inputmethod.InputMethodManager


class PublishedNotebookCommentsFragment : BaseFragment(), CommentsAdapter.CommentsItemListener {

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
        subscribe()
        setupListeners()
        setupRecyclerView()
    }

    private fun subscribe() {

        viewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {

        })

        viewModel.getCommentsLiveData().observe(viewLifecycleOwner, Observer {
            adapter.items = MutableList(it.size) { index: Int -> it[index] }
        })

        viewModel.commentsStateLiveData.onHandle(viewLifecycleOwner) {
            Timber.d("State $it")
            it.commentAdded?.let {
                commentsRecyclerView.smoothScrollToPosition(0)
                adapter.addComment(it)

            }
            it.commentDeleted?.let { adapter.deleteComment(it) }
            it.commentUpdated?.let { adapter.updateComment(it) }
        }
    }


    private fun setupListeners() {
        commentEditText.listener = object : CommentEditText.CommentEditTextListener {
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
        PublishedNotebookOptionsMenu.show(childFragmentManager) {
            Timber.d("onSelected")
            when(it) {
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

    companion object {

        private const val ARG_NOTEBOOK_ID = "notebookid"
        fun newInstance(notebookId: String): PublishedNotebookCommentsFragment {
            return PublishedNotebookCommentsFragment().apply {
                arguments = Bundle().apply { putString(ARG_NOTEBOOK_ID, notebookId) }
            }
        }
    }
}
