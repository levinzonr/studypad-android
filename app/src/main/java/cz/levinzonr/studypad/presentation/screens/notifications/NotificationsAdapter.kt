package cz.levinzonr.studypad.presentation.screens.notifications

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.*
import cz.levinzonr.studypad.domain.models.Notification
import kotlinx.android.synthetic.main.item_notification.view.*

class NotificationsAdapter(private val listener: NotificationItemsListener) : ListAdapter<Notification, NotificationsAdapter.ViewHolder>(
    DiffCallback()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.context.layoutInflater.inflate(R.layout.item_notification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    interface NotificationItemsListener {
        fun onNotificationClicked(notification: Notification)
        fun onNotificationReadClicked(notification: Notification)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val context = view.context
        fun bindView(notification: Notification) {
            view.notificationBodyTv.text = notification.body
            view.notificationReadBtn.setVisible(!notification.read)
            when(notification.type) {
               NotificationType.Comment -> {
                    view.notificationTimeTv.text = "${context.getString(R.string.notification_comment_title)} • ${notification.time.formatTime(context)}"
                    val body = context.getString(R.string.notification_comment_message, notification.userName, notification.notebookName)
                    view.notificationBodyTv.setSpannableText(body, notification.userName, notification.notebookName)
                    view.notificationTypeIv.setImageResource(R.drawable.ic_comment)
                    val color = ContextCompat.getColor(view.context, R.color.blue)
                    view.notificationTypeIv.imageTintList = ColorStateList.valueOf(color)
                }
               NotificationType.Update -> {
                   view.notificationTimeTv.text = "${context.getString(R.string.notification_update_title)} • ${notification.time.formatTime(context)}"
                    val color = ContextCompat.getColor(view.context, R.color.oragne)
                    val body = context.getString(R.string.notification_update_message, notification.notebookName)
                    view.notificationBodyTv.setSpannableText(body, notification.notebookName)
                    view.notificationTypeIv.setImageResource(R.drawable.ic_sync_black_24dp)
                    view.notificationTypeIv.imageTintList = ColorStateList.valueOf(color)
                }
                NotificationType.Suggestion -> {
                    view.notificationTimeTv.text = "${context.getString(R.string.notification_update_title)} • ${notification.time.formatTime(context)}"
                    val color = ContextCompat.getColor(view.context, R.color.green)
                    val text = context.getString(R.string.notification_suggestion_message, notification.notebookName)
                    view.notificationBodyTv.setSpannableText(text, notification.notebookName)
                    view.notificationTypeIv.setImageResource(R.drawable.ic_support)
                    view.notificationTypeIv.imageTintList = ColorStateList.valueOf(color)
                }
            }

            view.setOnClickListener { listener.onNotificationClicked(notification) }
            view.notificationReadBtn.setOnClickListener { listener?.onNotificationReadClicked(notification) }
        }
    }

    class DiffCallback: DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem == newItem
        }
    }

}