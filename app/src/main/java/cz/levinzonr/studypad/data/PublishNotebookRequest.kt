package cz.levinzonr.studypad.data

class PublishNotebookRequest(
        val title: String?,
        val universityId: Long?,
        val tags: Set<String>?,
        val topic: Long?,
        val description: String?,
        val notebookId: Long
)