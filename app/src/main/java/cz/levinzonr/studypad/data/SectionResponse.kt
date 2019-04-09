package cz.levinzonr.studypad.data

import cz.levinzonr.studypad.domain.models.PublishedNotebook

data class SectionResponse(
    val type: String,
    val items: List<PublishedNotebook.Feed>
)