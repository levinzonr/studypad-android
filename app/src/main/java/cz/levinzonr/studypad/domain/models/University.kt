package cz.levinzonr.studypad.domain.models

import android.os.Parcel
import android.os.Parcelable

data class University(
    val id: Long,
    val fullName: String,
    val shortName: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(fullName)
        parcel.writeString(shortName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<University> {
        override fun createFromParcel(parcel: Parcel): University {
            return University(parcel)
        }

        override fun newArray(size: Int): Array<University?> {
            return arrayOfNulls(size)
        }
    }
}