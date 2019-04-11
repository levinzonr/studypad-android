package cz.levinzonr.studypad.presentation.screens.sharinghub.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.first
import cz.levinzonr.studypad.onQueryTextChanged
import cz.levinzonr.studypad.presentation.adapters.PublishedNotebooksAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.common.VerticalSpaceItemDecoration
import cz.levinzonr.studypad.presentation.screens.library.publish.TagSearchDialog
import cz.levinzonr.studypad.presentation.screens.library.publish.TopicSearchDialog
import cz.levinzonr.studypad.presentation.screens.onboarding.signup.UniversitySelectorFragment
import cz.levinzonr.studypad.presentation.screens.selectors.MultipleTopicsSelector
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.fragment_notebooks_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NotebooksSearchFragment : BaseFragment() {

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
            adapter.items = it
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        resultsRv.addItemDecoration(VerticalSpaceItemDecoration(16))

    }

    private fun setupListeners() {

        searchOptionCategory.setOnClickListener {
            val alreadySelected = viewModel.currentSearchState?.topic ?: listOf()
            MultipleTopicsSelector.show(childFragmentManager, alreadySelected) {
                viewModel.onCategoriesOptionChanged(it)
            }
        }

        searchOptionTags.setOnClickListener {
            val tags = viewModel.currentSearchState?.tags ?: listOf()
            TagSearchDialog.show(childFragmentManager, tags.toSet()) { tag, enable ->
                viewModel.onTagsOptionChanged(tag, enable)
            }
        }

        searchOptionUniversity.setOnClickListener {
            UniversitySelectorFragment.show(childFragmentManager) {
                viewModel.onUniversityOptionChanged(it)
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
            searchOptionTags.text = "Tags"
        } else {
            searchOptionTags.text = searchState.tags.first(3).joinToString(",")
        }

        if (searchState.university != null) {
            searchOptionUniversity.text = searchState.university.fullName
        } else {
            searchOptionUniversity.text = "University"
        }

        if (searchState.topic.isEmpty()) {
            searchOptionCategory.text = "Category"
        } else {
            searchOptionCategory.text = searchState.topic.first(3).joinToString(",") { it.name }
        }

    }


}
