package cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.dp
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.common.VerticalSpaceItemDecoration
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.fragment_notebook_suggestions.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class NotebookSuggestionsFragment : BaseFragment(), SuggestionsAdapter.SuggestionItemListener {

    override val viewModel: NotebookSuggestionsViewModel by viewModel { parametersOf(args.notes, args.notebookId, args.authoredByMe) }

    private val adapter: SuggestionsAdapter by inject()
    private val args: NotebookSuggestionsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notebook_suggestions, container, false)
    }

    override fun subscribe() {
        viewModel.viewState.observeNonNull(viewLifecycleOwner) { state ->
            reviewBtn.setVisible(state.isReviewButtonEnabled)
            adapter.submitList(state.items)
            emptyView.configure(R.string.suggestion_empty)
            suggestionsRv.setVisible(state.items.isNotEmpty())
            emptyView.setVisible(state.items.isEmpty())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        reviewBtn.setOnClickListener { viewModel.onReviewButtonClicked() }
        emptyView.setActionButton {
            viewModel.refresh()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    override fun onSuggestionClicked(suggestionItem: SuggestionsModels.SuggestionItem) {
        SuggestionsInfoDialog.show(childFragmentManager, suggestionItem)
    }

    override fun showNetworkUnavailableError() {
        emptyView.setVisible(true)
        suggestionsRv.setVisible(false)
        emptyView.configureAsNetworkError()
    }

    private fun setupRecyclerView() {
        suggestionsRv.adapter = adapter
        suggestionsRv.layoutManager = LinearLayoutManager(context)
        adapter.listener = this
        suggestionsRv.addItemDecoration(VerticalSpaceItemDecoration(8.dp))
    }
    

}
