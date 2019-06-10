package cz.levinzonr.studypad.data

/**
 * Payload/body  for the note creation request
 */
data class CreateNoteRequest(
    val notebookId: String,
    val title: String,
    val content: String
)