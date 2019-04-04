package cz.levinzonr.studypad.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class University(
    val fullName: String,
    val location: Location,
    val id: Long
) : Parcelable


@Parcelize
data class Location(
    val country: String,
    val code: String
) : Parcelable