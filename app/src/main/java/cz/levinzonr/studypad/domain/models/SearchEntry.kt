package cz.levinzonr.studypad.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["query", "universityId", "orderBy", "topics", "tags"])
data class SearchEntry(
    val query: String = "",
    val university: University? = null,
    val orderBy: OrderBy = OrderBy.RECENT,
    val topics: List<Topic> = listOf(),
    val tags: List<String> = listOf(),
    val lastlyUsed: Long = System.currentTimeMillis(),
    val universityId: Long = university?.id ?: -1L
)


enum class OrderBy {
    RECENT, POPULARITY
}
