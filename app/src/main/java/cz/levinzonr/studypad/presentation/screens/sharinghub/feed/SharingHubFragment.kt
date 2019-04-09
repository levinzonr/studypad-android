package cz.levinzonr.studypad.presentation.screens.sharinghub.feed

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.lifecycle.Observer

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.data.SectionResponse
import cz.levinzonr.studypad.domain.models.*
import cz.levinzonr.studypad.first
import cz.levinzonr.studypad.presentation.adapters.NotificationsAdapter
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


class SharingHubFragment : BaseFragment(), NotificationsAdapter.NotificationItemsListener,
    PublishedNotebooksAdapter.PublishedNotebookItemListener {

    override val viewModel: SharingHubViewModel by viewModel()

    private val notificationsAdapter: NotificationsAdapter by inject { parametersOf(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_shared, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        searchEntryRv.addItemDecoration(VerticalSpaceItemDecoration(16))

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sharing, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.searchBtn -> viewModel.onSearchButtonClicked()
        }
        return true
    }

    override fun onNotificationClicked(notification: Notification) {

    }

    private fun subscribe() {

        viewModel.recentSearches.observe(this, Observer {
            searchEntryRv.adapter = notificationsAdapter
            notificationsAdapter.submitList(it.first(2))

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
        sectionView.sectionName.text = when (section.type) {
            SectionType.UNKNOWN -> "Others"
            SectionType.POPULAR -> "Popular"
            SectionType.RECENT -> "Recently added"
            SectionType.SCHOOL -> "From Your School"
        }
        sectionView.sectionRv.adapter = adapter
        sectionView.sectionSeeAllBtn.setOnClickListener { viewModel.onShowAllClicked(section) }
        adapter.listener = this
        adapter.items = section.items
        sectionView.layoutParams =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        sectionContainer.addView(sectionView)

    }

    override fun onPublishedNotebookClicked(publishedNotebook: PublishedNotebook.Feed) {
        viewModel.showDetail(publishedNotebook)
    }

}
