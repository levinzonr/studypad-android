package cz.levinzonr.studypad.presentation.screens.sharinghub.feed

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.lifecycle.Observer

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.data.SectionResponse
import cz.levinzonr.studypad.domain.models.*
import cz.levinzonr.studypad.dp
import cz.levinzonr.studypad.first
import cz.levinzonr.studypad.notifications.NotificationPayload
import cz.levinzonr.studypad.presentation.adapters.NotificationsAdapter
import cz.levinzonr.studypad.presentation.adapters.PublishedNotebooksAdapter
import cz.levinzonr.studypad.presentation.adapters.SearchEntryAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.base.NotificationHandler
import cz.levinzonr.studypad.presentation.common.VerticalSpaceItemDecoration
import cz.levinzonr.studypad.presentation.screens.notifications.NotificationType
import cz.levinzonr.studypad.presentation.screens.notifications.NotificationsFragment
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.fragment_shared.*
import kotlinx.android.synthetic.main.view_section.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber


class SharingHubFragment : BaseFragment(),  NotificationHandler,  NotificationsAdapter.NotificationItemsListener,
    PublishedNotebooksAdapter.PublishedNotebookItemListener {

    override val viewModel: SharingHubViewModel by viewModel()

    private lateinit var notificationsFragment: NotificationsFragment


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
        notificationsFragment = NotificationsFragment.newInstance(true)
        childFragmentManager.beginTransaction()
            .add(R.id.container, NotificationsFragment.newInstance(true), "tag")
            .commit()
    }

    override fun handleNotification(type: NotificationType, notificationPayload: NotificationPayload) {
        Timber.d("Handle $type")
        (childFragmentManager.findFragmentByTag("tag") as? NotificationsFragment?)?.handleNotification(type, notificationPayload)
       // notificationsFragment.handleNotification(type, notificationPayload)
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
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(0, 2.dp, 0, 0)
        sectionView.layoutParams = params

        sectionContainer.addView(sectionView)

    }

    override fun onPublishedNotebookClicked(publishedNotebook: PublishedNotebook.Feed) {
        viewModel.showDetail(publishedNotebook)
    }

}
