package cz.levinzonr.studypad.domain.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProfile(
    val uuid: String,
    val firstName: String,
    val lastName: String,
    val university: University?,
    val photoUrl: String?,
    val newUser: Boolean = false,
    val displayName: String,
    val unreadNotifications: Int
) : Parcelable