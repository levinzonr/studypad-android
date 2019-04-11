package cz.levinzonr.studypad.notifications

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotificationPayload(
    val id: Long,
    val body: String,
    val title: String,
    val userId: String,
    val type: String,
    val read: Boolean,
    val notebookId: String,
    val createdAt: Long = System.currentTimeMillis()) : Parcelable