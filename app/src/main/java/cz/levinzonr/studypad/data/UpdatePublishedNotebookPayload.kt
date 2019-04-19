package cz.levinzonr.studypad.data

data class UpdatePublishedNotebookPayload(
    val title: String?,
    val description: String?,
    val languageCode: String?,
    val topicId: Long?,
    val universityId: Long?,
    val tags: Set<String>?
)