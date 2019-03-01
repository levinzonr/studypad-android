package cz.levinzonr.studypad.domain.models

import android.os.Parcel
import android.os.Parcelable

object PublishedNotebook {

    data class Feed(
        val title: String,
        val description: String?,
        val notesCount: Int,
        val tags: Set<String>,
        val commentCount: Int,
        val author: UserProfile,
        val id: String,
        val topic: String,
        val lastUpdate: Long = System.currentTimeMillis(),
        val languageCode: String? = null

    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            setOf<String>(),
            parcel.readInt(),
            parcel.readParcelable(UserProfile::class.java.classLoader),
            parcel.readString(),
            parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(title)
            parcel.writeString(description)
            parcel.writeInt(notesCount)
            parcel.writeStringList(tags.toList())
            parcel.writeInt(commentCount)
            parcel.writeParcelable(author, flags)
            parcel.writeString(id)
            parcel.writeString(topic)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Feed> {
            override fun createFromParcel(parcel: Parcel): Feed {
                return Feed(parcel)
            }

            override fun newArray(size: Int): Array<Feed?> {
                return arrayOfNulls(size)
            }
        }

    }

    data class Detail(
        val id: String,
        val title: String,
        val description: String,
        val notes: List<Note>,
        val tags: Set<String>,
        val author: UserProfile,
        val comments: List<Comment>,
        val topic: String,
        val lastUpdate: Long,
        val languageCode: String? = null,
        val status: String
    ) {

        val isSavedAlready: Boolean = status == "update"
    }

    data class Note(
        val title: String,
        val content: String
    )

    data class Comment(
        val id: Long,
        val author: UserProfile,
        val content: String,
        val dateCreated: Long,
        val edited: Boolean
    )

}