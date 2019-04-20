package cz.levinzonr.studypad.presentation.screens.sharinghub.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import cz.levinzonr.studypad.*
import cz.levinzonr.studypad.domain.models.InteractorResult

import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.presentation.adapters.PublishedNotebooksAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.common.VerticalSpaceItemDecoration
import cz.levinzonr.studypad.presentation.screens.library.publish.TagSearchDialog
import cz.levinzonr.studypad.presentation.screens.library.publish.TopicSearchDialog
import cz.levinzonr.studypad.presentation.screens.onboarding.signup.UniversitySelectorFragment
import cz.levinzonr.studypad.presentation.screens.selectors.MultipleTopicsSelector
import kotlinx.android.synthetic.main.fragment_notebooks_search.*
import kotlinx.android.synthetic.main.fragment_notebooks_search.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class NotebooksSearchFragment : BaseFragment(), PublishedNotebooksAdapter.PublishedNotebookItemListener {

    private val args: NotebooksSearchFragmentArgs by navArgs()
    override val viewModel: NotebooksSearchViewModel by viewModel { parametersOf(args.initState) }
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
        viewModel.searchStateLiveData.observe(viewLifecycleOwner, Observer {
            updateSearchState(it)
        })

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
                    showEmptyView(NotebookSearchModels.EmptyType.Error)
                }
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        if (args.initState?.isDefaultState == true) {
            searchView.requestFocus()
        }
        resultsRv.addItemDecoration(VerticalSpaceItemDecoration(16))
    }

    override fun onPublishedNotebookClicked(publishedNotebook: PublishedNotebook.Feed) {
        viewModel.onNotebookSelected(publishedNotebook)
    }

    override fun showLoading(isLoading: Boolean) {
        progressBar.setVisible(isLoading)
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
                searchView.hideKeyboard()
                UniversitySelectorFragment.show(childFragmentManager) {
                    viewModel.onUniversityOptionChanged(it)
                }
            }
        }

        searchView.onQueryTextChanged {
            viewModel.onQueryChanged(it)
        }
    }

    private fun showEmptyView(type: NotebookSearchModels.EmptyType) {
        when (type) {
            NotebookSearchModels.EmptyType.Error -> {
                resultsRv.setVisible(false)
                emptyView.configure("Error", "Error while loading notebooks")
            }
            NotebookSearchModels.EmptyType.Default -> {
                emptyView.configure(
                    "Explore!",
                    " Start exploring new notebooks for your collection by choosing a search option"
                )
            }
            NotebookSearchModels.EmptyType.Empty -> {
                emptyView.configure("Nothing found", "There are notebooks found using these search options")
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


}
