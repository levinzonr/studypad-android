package cz.levinzonr.studypad.domain.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * Main entity to represent Notebook
 * @param id - id of the notebook
 * @param name - name of the notebook
 * @param notesCount - number of notes inside
 * @param authoredByMe - flag to tell if the notebook is authored by user
 * @param color - gradient color
 */
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

/**
 * Class that represents version state of the notebook in regards to its published version
 */
sealed class State {

    /**
     * Both local and published version are equal
     * there are no updates/modifications and version number is the same
     */
    object UpToDate : State()

    /**
     * Local version is outdated
     * published notebook version is higher than the local's one
     */
    object UpdateAvailable: State()

    /**
     * Published notebook can be saved/imported
     * User does not have this notebook saved yet
     */
    object SaveAvailable: State()

    /**
     * Local version has derived from the published one
     * User has updated the local version of his notebook and now able to either create a suggestion
     * or apply his/her changes directly
     */
    data class MergeAvailable(val authoredByMe: Boolean): State()

}

