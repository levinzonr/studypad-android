package cz.levinzonr.studypad.presentation.adapters

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Color
import cz.levinzonr.studypad.domain.models.Notification
import cz.levinzonr.studypad.formatTime
import cz.levinzonr.studypad.layoutInflater
import kotlinx.android.synthetic.main.item_notification.view.*

class NotificationsAdapter(private val listener: NotificationsAdapter.NotificationItemsListener) : ListAdapter<Notification, NotificationsAdapter.ViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.context.layoutInflater.inflate(R.layout.item_notification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    interface NotificationItemsListener {
        fun onNotificationClicked(notification: Notification)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(notification: Notification) {
            view.notificationBodyTv.text = notification.body

            when(notification.type) {
                "comment" -> {
                    view.notificationTimeTv.text = "New Comment • ${notification.time.formatTime()}"

                    view.notificationTypeIv.setImageResource(R.drawable.ic_comment)
                    val color = ContextCompat.getColor(view.context, R.color.blue)
                    view.notificationTypeIv.imageTintList = ColorStateList.valueOf(color)
                }
                "update" -> {
                    view.notificationTimeTv.text = "Update Available • ${notification.time.formatTime()}"
                    val color = ContextCompat.getColor(view.context, R.color.oragne)
                    view.notificationTypeIv.setImageResource(R.drawable.ic_sync_black_24dp)
                    view.notificationTypeIv.imageTintList = ColorStateList.valueOf(color)
                }
            }

            view.setOnClickListener { listener.onNotificationClicked(notification) }
        }
    }

    class DiffCallback: DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem.id == newItem.id
        }
    }

}