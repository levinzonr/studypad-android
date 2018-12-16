package cz.levinzonr.studyhub.domain

data class Notebook(
    val id: Long,
    val name: String,
    val notesCount: Int,
    val color: Color  = Color("#33ccff", "#ff99cc")
)

data class Color(
    val startColor: String,
    val endColor: String
) {
    fun toIntArray() : IntArray {
        return intArrayOf(android.graphics.Color.parseColor(startColor), android.graphics.Color.parseColor(endColor))
    }
}