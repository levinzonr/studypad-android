package cz.levinzonr.studypad.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Topic(val id: Long, val name: String) : Parcelable