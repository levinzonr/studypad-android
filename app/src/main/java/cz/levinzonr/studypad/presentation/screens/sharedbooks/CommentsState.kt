package cz.levinzonr.studypad.presentation.screens.sharedbooks

import cz.levinzonr.studypad.domain.models.PublishedNotebook

data class CommentsState(
    val commentDeleted: PublishedNotebook.Comment? = null,
    val commentUpdated: PublishedNotebook.Comment? = null,
    val commentAdded: PublishedNotebook.Comment? = null
)