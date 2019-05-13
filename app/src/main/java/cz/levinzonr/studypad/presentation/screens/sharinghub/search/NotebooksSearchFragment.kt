package cz.levinzonr.studypad.presentation.screens.sharinghub.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.*
import cz.levinzonr.studypad.domain.models.InteractorResult

import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.presentation.screens.sharinghub.feed.PublishedNotebooksAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.base.BottomSheetOptionsDialog
import cz.levinzonr.studypad.presentation.base.PublishedBookOptionsDialog
import cz.levinzonr.studypad.presentation.common.ToolbarSpaceDecoration
import cz.levinzonr.studypad.presentation.common.VerticalSpaceItemDecoration
import cz.levinzonr.studypad.presentation.screens.selectors.tag.TagSearchDialog
import cz.levinzonr.studypad.presentation.screens.selectors.topic.MultipleTopicsSelector
import cz.levinzonr.studypad.presentation.screens.selectors.university.UniversitySelectorViewModel
import kotlinx.android.synthetic.main.fragment_notebooks_search.*
import kotlinx.android.synthetic.main.view_empty_state.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber
import java.io.IOException

class NotebooksSearchFragment : BaseFragment(), PublishedNotebooksAdapter.PublishedNotebookItemListener {

    private val args: NotebooksSearchFragmentArgs by navArgs()
    override val viewModel: NotebooksSearchViewModel by viewModel { parametersOf(args.initState) }
    private val univerViewModel: UniversitySelectorViewModel by sharedViewModel()
    private lateinit var adapter: PublishedNotebooksAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notebooks_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = PublishedNotebooksAdapter(PublishedNotebooksAdapter.AdapterType.Full)
        resultsRv.adapter = adapter
        adapter.listener = this

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        if (args.initState?.isDefaultState == true) {
            searchView.requestFocus()
        }
        resultsRv.addItemDecoration(VerticalSpaceItemDecoration(16))
        resultsRv.addItemDecoration(ToolbarSpaceDecoration())
    }

    override fun onPublishedNotebookClicked(publishedNotebook: PublishedNotebook.Feed) {
        viewModel.onNotebookSelected(publishedNotebook)
    }

    override fun showLoading(isLoading: Boolean) {
        progressBar.setVisible(isLoading)
    }

    override fun showNetworkUnavailableError() {
        progressBar.setVisible(false)
        emptyView.configureAsNetworkError()
        emptyView.actionButton.visibility = View.VISIBLE
        emptyView.setVisible(true)
    }

    private fun setupListeners() {

        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            Timber.d("Has fouces; $hasFocus")
            if (hasFocus) searchView.showKeyboard()
        }

        searchOptionCategory.setFilterListener { clear ->
            if (clear) viewModel.onCategoriesOptionChanged(listOf())
            else {
                searchView.hideKeyboard()
                val alreadySelected = viewModel.currentSearchState?.topic ?: listOf()
                MultipleTopicsSelector.show(childFragmentManager, alreadySelected) {
                    viewModel.onCategoriesOptionChanged(it)
                }
            }
        }

        resultsRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    searchView.hideKeyboard()
                }
            }
        })

        searchOptionTags.setFilterListener { clear ->
            val tags = viewModel.currentSearchState?.tags ?: listOf()
            if (clear) viewModel.onTagsOptionChanged(setOf())
            else {
                searchView.hideKeyboard()
                TagSearchDialog.show2(childFragmentManager, tags.toSet()) {
                    viewModel.onTagsOptionChanged(it)
                }
            }
        }

        searchOptionUniversity.setFilterListener { clear ->
            if (clear) viewModel.onUniversityOptionChanged(null)
            else {
                viewModel.onSelectUniversityClicked()
                searchView.hideKeyboard()
            }
        }

        searchView.onQueryTextChanged {
            viewModel.onQueryChanged(it)
        }

        emptyView.setActionButton {
            viewModel.performSearch()
        }
    }

    override fun subscribe() {
        viewModel.searchStateLiveData.observe(viewLifecycleOwner, Observer {
            updateSearchState(it)
        })

        univerViewModel.universitySelectedEvent.observeNonNull(viewLifecycleOwner) {
            it?.handle { viewModel.onUniversityOptionChanged(it) }
        }

        viewModel.resultsLiveData.observeNonNull(viewLifecycleOwner) {
            Timber.d("Result: $it")
            when (it) {
                is InteractorResult.Loading -> showLoading(true)
                is InteractorResult.Success -> {
                    showLoading(false)
                    adapter.items = it.data
                    resultsRv.setVisible(true)
                    if (it.data.isEmpty()) showEmptyView(NotebookSearchModels.EmptyType.Empty) else {
                        emptyView.setVisible(false)
                    }
                }
                is InteractorResult.Error -> {
                    showLoading(false)
                    if (it.exception is IOException) showNetworkUnavailableError()
                    else showEmptyView(NotebookSearchModels.EmptyType.Error)
                }
            }

        }
    }

    private fun showEmptyView(type: NotebookSearchModels.EmptyType) {
        when (type) {
            NotebookSearchModels.EmptyType.Error -> {
                resultsRv.setVisible(false)
                emptyView.actionButton.setVisible(true)
                emptyView.actionButton.visibility = View.VISIBLE
                emptyView.configure(R.string.sharinghub_search_error_title)
            }
            NotebookSearchModels.EmptyType.Default -> {
                emptyView.actionButton.setVisible(false)
                emptyView.actionButton.visibility = View.GONE
                emptyView.configure(R.string.sharinghub_search_default_title, R.string.sharinghub_search_default_message)
            }
            NotebookSearchModels.EmptyType.Empty -> {
                emptyView.actionButton.setVisible(false)
                emptyView.actionButton.visibility = View.VISIBLE
                emptyView.configure(R.string.sharinghub_search_empty_title, R.string.sharinghub_search_empty_message)
            }
        }
        emptyView.setVisible(true)
    }


    private fun updateSearchState(searchState: NotebookSearchModels.SearchState) {

        resultsRv.setVisible(!searchState.isDefaultState)
        if (searchState.isDefaultState) {
            showEmptyView(NotebookSearchModels.EmptyType.Default)
        }

        if (searchState.tags.isEmpty()) {
            searchOptionTags.setChipInactive()
        } else {
            searchOptionTags.setChipActive(searchState.tags.first(3).joinToString(","))
        }

        if (searchState.university != null) {
            searchOptionUniversity.setChipActive(searchState.university.fullName)
        } else {
            searchOptionUniversity.setChipInactive()
        }

        if (searchState.topic.isEmpty()) {
            searchOptionCategory.setChipInactive()
        } else {
            searchOptionCategory.setChipActive(searchState.topic.first(3).joinToString(",") { it.name })
        }

    }

    override fun onPublishedNotebookOptionsClicked(publishedNotebook: PublishedNotebook.Feed) {
        BottomSheetOptionsDialog.builder<PublishedBookOptionsDialog>()
            .show(childFragmentManager) {
                when(it) {
                    R.id.publishedBookShareBtn -> shareMessage(publishedNotebook.title, publishedNotebook.id.toNotebookLink())
                    R.id.publishedBookCopyBtn -> copyToClipboard(publishedNotebook.id.toNotebookLink())
                }
            }
    }
}
