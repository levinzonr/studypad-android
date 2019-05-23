package cz.levinzonr.studypad.data

/**
 * Payload for the POST notebook request
 * @param name - notebook's name
 */
data class CreateNotebookRequest(
    val name: String
)