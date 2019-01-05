package cz.levinzonr.studypad.domain.models

object PublishedNotebook {

    data class Feed(
        val title: String,
        val description: String,
        val notesCount: Int,
        val commentCount: Int,
        val author: UserProfile,
        val id: String
    )

    data class Detail(
        val id: String,
        val title: String,
        val description: String,
        val notes: List<Note>,
        val tags: Set<String>,
        val author: UserProfile,
        val comments: List<Comment>
    )

    data class Note(
        val title: String,
        val content: String
    )

    data class Comment(
        val id: Long,
        val author: UserProfile,
        val content: String,
        val dateCreated: Long
    )

}