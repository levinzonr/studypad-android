package cz.levinzonr.studypad.data

data class PublishNotebookRequest(
        val title: String?,
        val universityId: Long?,
        val tags: Set<String>?,
        val topic: Long?,
        val description: String?,
        val notebookId: String,
        val languageCode: String
)