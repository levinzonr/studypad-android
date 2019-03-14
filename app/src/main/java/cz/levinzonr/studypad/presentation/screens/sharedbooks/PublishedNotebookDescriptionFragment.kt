package cz.levinzonr.studypad.presentation.screens.sharedbooks


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
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
import cz.levinzonr.studypad.presentation.common.DividerItemDecorator
import kotlinx.android.synthetic.main.fragment_published_notebook_description.*
import kotlinx.android.synthetic.main.fragment_published_notebook_description.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.security.InvalidParameterException


class PublishedNotebookDescriptionFragment : BaseFragment() {

    private val notebookId: String
        get() = arguments?.getString(ARG_NOTEBOOK_ID) ?: throw InvalidParameterException()

    private val viewModel: PublishedNotebookDetailViewModel by viewModel { parametersOf(notebookId) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_published_notebook_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveButton.setOnClickListener {
            viewModel.handleSaveAction()
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
                }
                is State.UpdateAvailable -> {
                    saveButton.setIconResource(R.drawable.ic_sync_black_24dp)
                    saveButton.setText("Update")
                }
                is State.UpToDate -> {
                    saveButton.setIconResource(R.drawable.ic_check_black_24dp)
                    saveButton.setText("Up to date")
                }
                is State.MergeAvailable -> {
                    saveButton.setIconResource(R.drawable.ic_round_publish_24px)
                    saveButton.setText("Apply changes")
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

        publishedBookNotesRv.adapter = NotePreviewAdapter(detail.notes.first(5))
        publishedBookNotesRv.layoutManager = LinearLayoutManager(context)

        publishBookDateTv.text = "last updated: ${detail.lastUpdate.formatTime()}"


    }

    companion object {
        private const val ARG_NOTEBOOK_ID = "notebookid"
        fun newInstance(notebookId: String): PublishedNotebookDescriptionFragment {
            return PublishedNotebookDescriptionFragment().apply {
                arguments = Bundle().apply { putString(ARG_NOTEBOOK_ID, notebookId) }
            }
        }
    }


}
