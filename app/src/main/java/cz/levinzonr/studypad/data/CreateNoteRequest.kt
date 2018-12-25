package cz.levinzonr.studypad.data

data class CreateNoteRequest(
    val notebookId: Long,
    val title: String,
    val content: String
)