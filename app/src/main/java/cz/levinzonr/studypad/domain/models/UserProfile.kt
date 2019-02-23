package cz.levinzonr.studypad.domain.models

import android.os.Parcel
import android.os.Parcelable

data class UserProfile(
    val uuid: String,
    val firstName: String,
    val lastName: String,
    val university: University?,
    val photoUrl: String?,
    val isNewUser: Boolean = false,
    val displayName: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(University::class.java.classLoader),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uuid)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeParcelable(university, flags)
        parcel.writeString(photoUrl)
        parcel.writeByte(if (isNewUser) 1 else 0)
        parcel.writeString(displayName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserProfile> {
        override fun createFromParcel(parcel: Parcel): UserProfile {
            return UserProfile(parcel)
        }

        override fun newArray(size: Int): Array<UserProfile?> {
            return arrayOfNulls(size)
        }
    }
}