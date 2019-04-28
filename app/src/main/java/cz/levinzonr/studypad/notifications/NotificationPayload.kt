package cz.levinzonr.studypad.notifications

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotificationPayload(
    val id: Long,
    val userId: String,
    val title: String,
    val body: String,
    val type: String,
    val notebookInfo: NotebookInfo,
    val userInfo: UserInfo? = null,
    var read: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()) : Parcelable


@Parcelize
data class NotebookInfo(val notebookId: String, val notebookName: String) : Parcelable

@Parcelize
data class UserInfo(val authorId: String, val authorName: String) : Parcelable