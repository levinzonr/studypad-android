package cz.levinzonr.studypad.data

data class CreateNoteRequest(
    val notebookId: String,
    val title: String,
    val content: String
)