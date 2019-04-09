package cz.levinzonr.studypad.domain.models

data class Section(val type: SectionType, val items: List<PublishedNotebook.Feed>)

enum class SectionType {
    SCHOOL, POPULAR, RECENT, UNKNOWN
}