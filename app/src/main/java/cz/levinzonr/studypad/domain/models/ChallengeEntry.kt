package cz.levinzonr.studypad.domain.models

import android.content.Context
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Relation
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengeType

@Entity(primaryKeys = ["type", "shuffled", "titleFirst", "notebookId"])
data class ChallengeEntry(

    val type: ChallengeType,
    val shuffled: Boolean,
    val titleFirst: Boolean,
    val notebook: Notebook,
    val percentage: Int
) {
    var notebookId: String = notebook.id

    fun optionsAsString(context: Context) : String {
        val shuffledStr = if (shuffled) "random order" else "default order"
        val titleStr = if (titleFirst) "title first" else "note first"
        val mastery = if (type != ChallengeType.Flashcards) "$percentage% mastery, " else ""
        return "$mastery$shuffledStr, $titleStr"
    }
}