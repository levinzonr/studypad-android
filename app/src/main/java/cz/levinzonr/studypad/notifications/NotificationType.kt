package cz.levinzonr.studypad.notifications

enum class NotificationType {
    Comment, Suggestion, Update;

    companion object {

        fun from(string: String): NotificationType {
            return when (string) {
                "suggestion" -> Suggestion
                "update" -> Update
                else -> Comment
            }
        }
    }
}