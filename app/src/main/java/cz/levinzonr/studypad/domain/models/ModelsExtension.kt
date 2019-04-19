package cz.levinzonr.studypad.domain.models

import cz.levinzonr.studypad.data.NotebooksResponse
import cz.levinzonr.studypad.data.SectionResponse


fun NotebooksResponse.toDomain() : Notebook {
    return Notebook(id, name, notesCount, authoredByMe,  color, publishedNotebookId)
}

fun SectionResponse.toDomain() : Section {
    val type = when (type) {
        "university" -> SectionType.SCHOOL
        "recent" -> SectionType.RECENT
        "popular" -> SectionType.POPULAR
        else -> SectionType.UNKNOWN
    }
    return Section(type, items)
}