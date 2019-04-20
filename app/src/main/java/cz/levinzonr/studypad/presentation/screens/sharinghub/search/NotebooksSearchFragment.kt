package cz.levinzonr.studypad.presentation.screens.sharinghub.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import cz.levinzonr.studypad.*

import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.presentation.adapters.PublishedNotebooksAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.common.VerticalSpaceItemDecoration
import cz.levinzonr.studypad.presentation.screens.library.publish.TagSearchDialog
import cz.levinzonr.studypad.presentation.screens.library.publish.TopicSearchDialog
import cz.levinzonr.studypad.presentation.screens.onboarding.signup.UniversitySelectorFragment
import cz.levinzonr.studypad.presentation.screens.selectors.MultipleTopicsSelector
import kotlinx.android.synthetic.main.fragment_notebooks_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class NotebooksSearchFragment : BaseFragment(), PublishedNotebooksAdapter.PublishedNotebookItemListener {

    private val args: NotebooksSearchFragmentArgs by navArgs()
    override val viewModel: NotebooksSearchViewModel by viewModel { parametersOf(args.initState) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notebooks_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel.searchStateLiveData.observe(viewLifecycleOwner, Observer {
            updateSearchState(it)
        })

        viewModel.resultsLiveData.observe(viewLifecycleOwner, Observer {
            val adapter = PublishedNotebooksAdapter(PublishedNotebooksAdapter.AdapterType.Full)
            resultsRv.adapter = adapter
            adapter.listener = this
            adapter.items = it
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        searchView.requestFocus()
        resultsRv.addItemDecoration(VerticalSpaceItemDecoration(16))
    }

    override fun onPublishedNotebookClicked(publishedNotebook: PublishedNotebook.Feed) {
        viewModel.onNotebookSelected(publishedNotebook)
    }

    private fun setupListeners() {

        searchView.setOnQueryTextFocusChangeListener {_, hasFocus ->
            Timber.d("Has fouces; $hasFocus")
            if (hasFocus)  searchView.showKeyboard()
        }

        searchOptionCategory.setFilterListener {clear ->
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

    private fun updateSearchState(searchState: NotebookSearchModels.SearchState) {

        resultsRv.setVisible(!searchState.isDefaultState)
        emptyView.setVisible(searchState.isDefaultState)

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
