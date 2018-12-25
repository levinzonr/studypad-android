package cz.levinzonr.studypad.domain.models

import android.os.Parcel
import android.os.Parcelable

data class Notebook(
    val id: Long,
    val name: String,
    val notesCount: Int,
    val color: Color = Color(
        "#33ccff",
        "#ff99cc"
    )
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readInt(),
        Color("#33ccff", "#ff99cc")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeInt(notesCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Notebook> {
        override fun createFromParcel(parcel: Parcel): Notebook {
            return Notebook(parcel)
        }

        override fun newArray(size: Int): Array<Notebook?> {
            return arrayOfNulls(size)
        }
    }
}

data class Color(
    val startColor: String,
    val endColor: String
) {
    fun toIntArray(): IntArray {
        return intArrayOf(android.graphics.Color.parseColor(startColor.trim()), android.graphics.Color.parseColor(endColor.trim()))
    }
}