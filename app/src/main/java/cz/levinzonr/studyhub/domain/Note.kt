package cz.levinzonr.studyhub.domain

data class Note(
    val id: Long,
    val title: String? = null,
    val content: String? = null
)