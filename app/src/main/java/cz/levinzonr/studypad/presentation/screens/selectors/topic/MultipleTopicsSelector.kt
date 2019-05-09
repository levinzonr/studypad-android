package cz.levinzonr.studypad.presentation.screens.selectors.topic


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.presentation.base.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_multiple_topics_selector.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MultipleTopicsSelector : BottomSheetDialog(), TopicsSelectionAdapter.TopicSelectionListener {

    override val viewModel: TopicSearchViewModel by viewModel()
    private val adapter: TopicsSelectionAdapter by inject { parametersOf(this) }
    private var listener: ((List<Topic>) -> Unit)? = null
    private var items: List<Topic> = listOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multiple_topics_selector, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topicsRv.adapter = adapter
        adapter.setSelected(items)
        viewModel.getTopicsObservable().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        closeBtn.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        private const val TAG = "selectiontag"
        fun show(fragmentManager: FragmentManager, alreadySelected: List<Topic>, onSelectionChanged: (List<Topic>) -> Unit) {
            val frag = fragmentManager.findFragmentByTag(TAG) as? MultipleTopicsSelector? ?: MultipleTopicsSelector()
            frag.listener = onSelectionChanged
            frag.items = alreadySelected
            frag.show(fragmentManager,
                TAG
            )
        }
    }

    override fun onTopicSelectionChanged(selected: List<Topic>) {
        listener?.invoke(selected)
    }
}
