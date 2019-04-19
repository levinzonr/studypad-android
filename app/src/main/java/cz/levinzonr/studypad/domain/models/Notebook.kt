package cz.levinzonr.studypad.domain.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Notebook(

    @PrimaryKey
    val id: String,
    val name: String,
    val notesCount: Int,
    val authoredByMe: Boolean,
    val color: Color = Color(
        "#33ccff",
        "#ff99cc"
    ),
    val publishedNotebookId: String?

) : Parcelable


@Parcelize
data class Color(
    val startColor: String,
    val endColor: String
) : Parcelable {
    fun toIntArray(): IntArray {
        return intArrayOf(android.graphics.Color.parseColor(startColor.trim()), android.graphics.Color.parseColor(endColor.trim()))
    }
}

sealed class State {

    object UpToDate : State()
    object UpdateAvailable: State()
    object SaveAvailable: State()
    data class MergeAvailable(val authoredByMe: Boolean): State()

}

