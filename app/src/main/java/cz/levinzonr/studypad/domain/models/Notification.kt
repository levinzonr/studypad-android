package cz.levinzonr.studypad.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Notification(
    val id: String,
    val type: String,
    val body: String = "Nullam quis risus eget urna mollis ornare vel eu leo.",
    val time: Long = System.currentTimeMillis()
) : Parcelable