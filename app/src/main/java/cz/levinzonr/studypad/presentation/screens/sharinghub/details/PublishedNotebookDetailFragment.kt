package cz.levinzonr.studypad.presentation.screens.sharinghub.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.notifications.NotificationPayload
import cz.levinzonr.studypad.presentation.adapters.ViewPagerAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.base.NotificationHandler
import cz.levinzonr.studypad.presentation.screens.notifications.NotificationType
import kotlinx.android.synthetic.main.fragment_published_notebook_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber


class PublishedNotebookDetailFragment: BaseFragment(), NotificationHandler {

    private val args: PublishedNotebookDetailFragmentArgs by navArgs()


    override val viewModel: PublishedNotebookDetailViewModel by viewModel { parametersOf(args.PublishedNotebookId) }

    private lateinit var adapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_published_notebook_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ViewPagerAdapter(args.PublishedNotebookId, args.PublishedNotebookFeed, childFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

    }


    override fun handleNotification(type: NotificationType, notificationPayload: NotificationPayload) {
        val current = adapter.fragments.get(viewPager.currentItem)
        Timber.d("hand $current")
        if (current is NotificationHandler) {
            Timber.d("hand")
            current.handleNotification(type, notificationPayload)
        }
    }
}
