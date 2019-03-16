package cz.levinzonr.studypad.domain.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Note(
    @PrimaryKey
    val id: Long,
    val title: String? = null,
    val content: String? = null,
    val notebookId: String
) : Parcelable