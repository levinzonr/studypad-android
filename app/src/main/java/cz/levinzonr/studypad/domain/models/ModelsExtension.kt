package cz.levinzonr.studypad.domain.models

import cz.levinzonr.studypad.data.NotebooksResponse


fun NotebooksResponse.toDomain() : Notebook {
    return Notebook(id, name, notesCount, color, publishedNotebookId)
}