package cz.levinzonr.studypad.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize



object PublishedNotebook {
    @Parcelize
    data class Feed(
        val title: String = "",
        val description: String? = null,
        val notesCount: Int = 0,
        val tags: Set<String> = setOf(),
        val commentCount: Int = 0,
        val author: UserProfile,
        val id: String,
        val topic: String,
        val lastUpdate: Long = System.currentTimeMillis(),
        val languageCode: String? = null,
        val university: University? = null

    ) : Parcelable

    @Parcelize
    data class VersionState(val id: Long, val version: Int, val modifications: List<Modification>) : Parcelable

    @Parcelize
    data class Modification(
        val id: Long,
        val type: String,
        val content: String,
        val title: String,
        val sourceId: Long?,
        val author: UserProfile
    ) : Parcelable

    enum class ModificationType {
        Added, Deleted, Updated;
        companion object {
            fun from(string: String) : ModificationType {
                return when(string) {
                    "upd" -> Updated
                    "add" -> Added
                    else -> Deleted
                }
            }
        }
    }




    data class Detail(
        val id: String,
        val title: String,
        val description: String?,
        val notes: List<Note>,
        val tags: Set<String>,
        val author: UserProfile,
        val comments: List<Comment>,
        val university: University?,
        val excludedFromSearch: Boolean,
        val topic: String,
        val lastUpdate: Long,
        val languageCode: String? = null,
        val versionState: VersionState,
        val authoredByMe: Boolean
    )

    data class Comment(
        val id: Long,
        val author: UserProfile,
        val content: String,
        val dateCreated: Long,
        val edited: Boolean
    )

}

val String.modType: PublishedNotebook.ModificationType
    get() = PublishedNotebook.ModificationType.from(this)