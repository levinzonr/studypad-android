package cz.levinzonr.studypad.presentation.screens.library.publish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.onQueryTextChanged
import cz.levinzonr.studypad.presentation.base.BottomSheetDialog
import kotlinx.android.synthetic.main.dialog_topic_search.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class TopicSearchDialog : BottomSheetDialog(), TopicsAdapter.TopicListener {

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView

    private val adapter: TopicsAdapter  by inject { parametersOf(this) }
    override val viewModel: TopicSearchViewModel by viewModel()

    private var callback: (Topic) -> Unit = {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = layoutInflater.inflate(R.layout.dialog_topic_search, container, false)
        searchView = view.topicSearchView
        recyclerView = view.topicRv
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecylcerView()
        viewModel.getTopicsObservable().observeNonNull(viewLifecycleOwner) {
            adapter.item = it
        }
        searchView.onQueryTextChanged {
            viewModel.filterTopics(it)
        }
    }



    private fun setupRecylcerView() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

    }


    override fun onTopicSelected(topic: Topic) {
        Timber.d("Topic: $topic")
        callback(topic)
        dismiss()
    }


    companion object {
        private const val TAG = "tagtopic"
        fun show(fg: FragmentManager, onSelected: (Topic) -> Unit) {
            val frag = fg.findFragmentByTag(TAG) as? TopicSearchDialog? ?: TopicSearchDialog()
            frag.callback = onSelected
            frag.show(fg, TAG)
        }
    }

}