package cz.levinzonr.studypad.presentation.screens.sharinghub

import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.models.State

object PublishedNotebookModels {

    data class DetailsViewState(
        val notebook: PublishedNotebook.Detail,
        val versionState: State
    )
}