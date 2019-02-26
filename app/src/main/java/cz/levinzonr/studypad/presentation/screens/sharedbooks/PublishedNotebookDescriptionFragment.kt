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
import cz.levinzonr.studypad.first
import cz.levinzonr.studypad.loadAuthorImage
import cz.levinzonr.studypad.presentation.adapters.NotePreviewAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.common.DividerItemDecorator
import kotlinx.android.synthetic.main.fragment_published_notebook_description.*
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
        viewModel.getSharedDetailLiveData().observe(viewLifecycleOwner, Observer {
            showDetail(it)
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
        publishedBookNotesRv.addItemDecoration(DividerItemDecorator(context!!))


    }

    companion object {
        private const val ARG_NOTEBOOK_ID = "notebookid"
        fun newInstance(notebookId: String) : PublishedNotebookDescriptionFragment {
            return PublishedNotebookDescriptionFragment().apply {
                arguments = Bundle().apply { putString(ARG_NOTEBOOK_ID, notebookId) }
            }
        }
    }


}
