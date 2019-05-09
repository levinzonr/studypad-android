package cz.levinzonr.studypad.presentation.screens.sharinghub.feed

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import cz.levinzonr.studypad.*

import cz.levinzonr.studypad.domain.models.*
import cz.levinzonr.studypad.notifications.NotificationPayload
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.base.BottomSheetOptionsDialog
import cz.levinzonr.studypad.presentation.base.NotificationHandler
import cz.levinzonr.studypad.presentation.base.PublishedBookOptionsDialog
import cz.levinzonr.studypad.presentation.screens.notifications.NotificationType
import cz.levinzonr.studypad.presentation.screens.notifications.NotificationsFragment
import kotlinx.android.synthetic.main.fragment_shared.*
import kotlinx.android.synthetic.main.view_section.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class SharingHubFragment : BaseFragment(),  NotificationHandler,
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
            .replace(R.id.container, NotificationsFragment.newInstance(true), "tag")
            .commit()

        notificationsSeeAllBtn.setOnClickListener { viewModel.onShowAllNotificationsClicked() }

    }

    override fun showNetworkUnavailableError() {
        emptyView.configureAsNetworkError()
        sectionContainer.setVisible(false)
        emptyView.setVisible(true)
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

    override fun subscribe() {
        viewModel.dataSource.observe(viewLifecycleOwner, Observer {
            emptyView.setVisible(false)
            sectionContainer.setVisible(true)
            Timber.d("state: $it")
            if (sectionContainer.childCount <= it.count()) {
                it.forEach(this::addSection)
            }
        })

    }

    override fun showLoading(isLoading: Boolean) {
        progressBar.setVisible(isLoading)
    }

    private fun addSection(section: Section) {
        Timber.d("adde sectiom")
        val adapter =
            PublishedNotebooksAdapter(PublishedNotebooksAdapter.AdapterType.Short)
        val sectionView = LayoutInflater.from(context).inflate(R.layout.view_section, null, false)
        when (section.type) {
            SectionType.UNKNOWN -> {
                sectionView.sectionImage.setVisible(false)
                sectionView.sectionName.text = "Others"
            }
            SectionType.POPULAR -> {
                sectionView.sectionImage.setImageResource(R.drawable.ic_whatshot_black_24dp)
                sectionView.sectionName.text = getString(R.string.sharinghub_section_popular)
            }
            SectionType.RECENT ->  {
                sectionView.sectionImage.setImageResource(R.drawable.ic_access_time_black_24dp)
                sectionView.sectionName.text = getString(R.string.sharinghub_section_recent)
            }
            SectionType.SCHOOL -> {
                sectionView.sectionImage.setImageResource(R.drawable.ic_school_black_24dp)
                sectionView.sectionName.text = getString(R.string.sharinghub_section_uni)
            }
        }

        sectionView.sectionRv.adapter = adapter
        sectionView.sectionSeeAllBtn.setOnClickListener { viewModel.onShowAllClicked(section) }
        adapter.listener = this
        adapter.items = section.items
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(8.dp, 8.dp, 8.dp, 8.dp)
        sectionView.layoutParams = params
        sectionContainer.addView(sectionView)

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

    override fun onPublishedNotebookClicked(publishedNotebook: PublishedNotebook.Feed) {
        viewModel.showDetail(publishedNotebook)
    }

}
