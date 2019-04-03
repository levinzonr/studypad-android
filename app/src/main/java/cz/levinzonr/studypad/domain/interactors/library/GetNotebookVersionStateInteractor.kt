package cz.levinzonr.studypad.domain.interactors.library

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.models.State
import cz.levinzonr.studypad.domain.repository.NotebookRepository
import timber.log.Timber
import java.lang.Exception

class GetNotebookVersionStateInteractor(private val notebookRepository: NotebookRepository): BaseInputInteractor<PublishedNotebook.Detail, State>() {

    override suspend fun executeOnBackground(input: PublishedNotebook.Detail): State {
        val alreadyImported = notebookRepository
            .getNotebooks()
            .filter { it.state != null }
            .firstOrNull { input.id == it.publishedNotebookId }

        Timber.d("Already improted: $alreadyImported")
        Timber.d("Version: ${alreadyImported?.state?.version} vs ${input.versionState.version}, ${alreadyImported?.state?.modifications}")
        return if (alreadyImported == null) {
            State.SaveAvailable
        } else {
            val state = alreadyImported.state ?: throw Exception()
            when {
                input.versionState.version > state.version && state.modifications.isEmpty() ->  State.UpdateAvailable
                input.versionState.version == state.version && state.modifications.isEmpty() -> State.UpToDate
                input.versionState.version == state.version && !state.modifications.isEmpty() -> State.MergeAvailable(input.authoredByMe)
                else -> State.UpdateAvailable
            }
        }
    }
}