package cz.levinzonr.studypad.data

import cz.levinzonr.studypad.domain.models.Color
import cz.levinzonr.studypad.domain.models.PublishedNotebook

data class NotebooksResponse(

    val id: String,
    val name: String,
    val notesCount: Int,
    val authoredByMe: Boolean,
    val color: Color = Color(
        "#33ccff",
        "#ff99cc"
    ),
    val publishedNotebookId: String?,
    val state: PublishedNotebook.VersionState? = null
)