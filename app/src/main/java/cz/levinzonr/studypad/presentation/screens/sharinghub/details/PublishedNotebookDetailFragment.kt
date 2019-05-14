package cz.levinzonr.studypad.presentation.screens.sharinghub.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.notifications.NotificationPayload
import cz.levinzonr.studypad.presentation.adapters.ViewPagerAdapter
import cz.levinzonr.studypad.presentation.base.BackButtonHandler
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.base.NotificationHandler
import cz.levinzonr.studypad.presentation.screens.notifications.NotificationType
import kotlinx.android.synthetic.main.fragment_published_notebook_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber


class PublishedNotebookDetailFragment: BaseFragment(), NotificationHandler, BackButtonHandler {

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
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}


            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                adapter.fragments.forEachIndexed { i: Int, f: BaseFragment -> if (i != position) f.onLoseFocus() }
            }

            override fun onPageSelected(position: Int) {
                adapter.fragments.forEachIndexed { i: Int, f: BaseFragment -> if (i != position) f.onLoseFocus() }
            }
        })
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun subscribe() {


    }

    override fun handleBackButton() {
        val current = adapter.fragments.get(viewPager.currentItem)
        Timber.d("hand $current")
        if (current is BackButtonHandler) {
            Timber.d("hand")
            current.handleBackButton()
        } else {
            findNavController().navigateUp()
        }
    }

    override fun handleNotification(type: NotificationType, notificationPayload: NotificationPayload) {
        val current = adapter.fragments.get(viewPager.currentItem)
        Timber.d("hand $current")
        // TODO Find a better way to handle 'em
        if (current is NotificationHandler && !current.isDetached) {
            Timber.d("hand")
            current.handleNotification(type, notificationPayload)
        }
    }
}
