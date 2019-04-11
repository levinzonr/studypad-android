package cz.levinzonr.studypad.domain.models

import android.os.Parcelable
import cz.levinzonr.studypad.presentation.screens.notifications.NotificationType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Notification(
    val id: String,
    val type: String,
    val body: String = "Nullam quis risus eget urna mollis ornare vel eu leo.",
    val time: Long = System.currentTimeMillis()
) : Parcelable {

    fun type() : NotificationType {
        return NotificationType.valueOf(type.capitalize())
    }
}