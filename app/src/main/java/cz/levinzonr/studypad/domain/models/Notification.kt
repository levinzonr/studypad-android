package cz.levinzonr.studypad.domain.models

import android.os.Parcelable
import cz.levinzonr.studypad.presentation.screens.notifications.NotificationType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Notification(
    val id: Long,
    val type: NotificationType,
    val read: Boolean,
    val notebookId: String,
    val body: String = "Nullam quis risus eget urna mollis ornare vel eu leo.",
    val time: Long = System.currentTimeMillis(),
    val notebookName: String,
    val userName: String? = null
) : Parcelable