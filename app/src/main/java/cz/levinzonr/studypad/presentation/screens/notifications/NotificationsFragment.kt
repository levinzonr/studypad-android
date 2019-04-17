package cz.levinzonr.studypad.presentation.screens.notifications


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Notification
import cz.levinzonr.studypad.first
import cz.levinzonr.studypad.notifications.NotificationPayload
import cz.levinzonr.studypad.presentation.adapters.NotificationsAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.base.NotificationHandler
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.fragment_notifications.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class NotificationsFragment : BaseFragment(), NotificationsAdapter.NotificationItemsListener, NotificationHandler {

    override val viewModel: NotificationsViewModel by viewModel { parametersOf(isPreview) }

    private val notificationsAdapter: NotificationsAdapter by inject { parametersOf(this) }


    private val isPreview: Boolean
        get() = arguments?.getBoolean(ARG_PREVIEW) ?: false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        notificationsRv.adapter = notificationsAdapter
        viewModel.notifications.observe(this, Observer {
            notificationsRv.setVisible(it.isNotEmpty())
            emptyView.setVisible(it.isEmpty())
            notificationsAdapter.submitList(it)
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyView.configure("Up to Date", "No new notifcations received")
    }

    override fun showLoading(isLoading: Boolean) {
        progressBar.setVisible(isLoading)
        emptyView.setVisible(!isLoading)
    }

    override fun handleNotification(type: NotificationType, notificationPayload: NotificationPayload) {
        Timber.d("hndel $type")
        viewModel.refresh()
    }

    override fun onNotificationReadClicked(notification: Notification) {
        viewModel.markAsRead(listOf(notification))
    }

    override fun onNotificationClicked(notification: Notification) {
        viewModel.onNotificationClicked(notification)
    }

    companion object {

        private const val ARG_PREVIEW = "arg_preview"

        fun newInstance(isPreview: Boolean = false) : NotificationsFragment {
            return NotificationsFragment().apply {
                arguments = Bundle().apply { putBoolean(ARG_PREVIEW, isPreview) }
            }
        }
    }

}
