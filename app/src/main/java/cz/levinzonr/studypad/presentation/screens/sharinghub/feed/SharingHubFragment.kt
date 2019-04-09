package cz.levinzonr.studypad.presentation.screens.sharinghub.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.data.SectionResponse
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.models.SearchEntry
import cz.levinzonr.studypad.domain.models.Section
import cz.levinzonr.studypad.domain.models.SectionType
import cz.levinzonr.studypad.first
import cz.levinzonr.studypad.presentation.adapters.PublishedNotebooksAdapter
import cz.levinzonr.studypad.presentation.adapters.SearchEntryAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.common.VerticalSpaceItemDecoration
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.fragment_shared.*
import kotlinx.android.synthetic.main.view_section.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class SharingHubFragment : BaseFragment(), PublishedNotebooksAdapter.PublishedNotebookItemListener, SearchEntryAdapter.SearchEntriesListener {

    override val viewModel : SharingHubViewModel by viewModel()

    private val searchEntryAdapter: SearchEntryAdapter by inject { parametersOf(this)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shared, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        searchEntryRv.addItemDecoration(VerticalSpaceItemDecoration(16))

    }

    private fun subscribe() {

        viewModel.recentSearches.observe(this, Observer {
            searchEntryRv.adapter = searchEntryAdapter
            searchEntryAdapter.submitList(it.first(2))

        })

        viewModel.dataSource.observe(this, Observer {
           it.forEach(this::addSection)
        })

        viewModel.loadingLiveData.observe(this, Observer {
            progressBar.setVisible(it)
        })

        viewModel.errorLiveData.observe(this, Observer {
            it.handle {
                showToast(it)
            }
        })
    }

    private fun addSection(section: Section) {
        val adapter = PublishedNotebooksAdapter(PublishedNotebooksAdapter.AdapterType.Short)
        val sectionView = LayoutInflater.from(context).inflate(R.layout.view_section, null, false)
        sectionView.sectionName.text = when(section.type) {
            SectionType.UNKNOWN -> "Others"
            SectionType.POPULAR -> "Popular"
            SectionType.RECENT -> "Recently added"
            SectionType.SCHOOL -> "From Your School"
        }
        sectionView.sectionRv.adapter = adapter
        sectionView.sectionSeeAllBtn.setOnClickListener { viewModel.onShowAllClicked(section) }
        adapter.listener = this
        adapter.items = section.items
        sectionView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        sectionContainer.addView(sectionView)

    }

    override fun onSearchEntrieClicked(searchEntry: SearchEntry) {
        viewModel.onSearchEntryClicked(searchEntry)
    }

    override fun onPublishedNotebookClicked(publishedNotebook: PublishedNotebook.Feed) {
        viewModel.showDetail(publishedNotebook)
    }
}
