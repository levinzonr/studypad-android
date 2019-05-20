package cz.levinzonr.studypad.domain.interactors.library

import cz.levinzonr.studypad.domain.interactors.BaseInteractor
import cz.levinzonr.studypad.domain.repository.NotebookRepository
import cz.levinzonr.studypad.domain.repository.NotesRepository
import timber.log.Timber

/**
 * Interactor to synchronize local notebook library with the remote data source
 * @param notebookRepository Repository with the notebooks
 * @param notesRepository repository with the notes
 *
 */
class LibrarySyncInteractor(
    private val notebookRepository: NotebookRepository,
    private val notesRepository: NotesRepository
) : BaseInteractor<Unit>() {



    override suspend fun executeOnBackground() {
        val remoteNotebooksIds = notebookRepository.getNotebooks().map { it.id }
        val localNotebooksIds = notebookRepository.getStoredNotebooks().map { it.id }

        // Find Notebooks thats been removed
        val removedIds = localNotebooksIds.filterNot { local -> remoteNotebooksIds.any { it == local } }
        Timber.d("Following notebooks removed: $removedIds")

        for (id in removedIds) {
            val notes = notesRepository.getStoredNotes(id).map { it.id }
            notebookRepository.deleteLocally(id)
            notes.forEach { notesRepository.deleteNote(it) }
        }

        val updatedLocals = notebookRepository.getStoredNotebooks().map { it.id }

        for (id in updatedLocals) {
            val notesRemote = notesRepository.getNotesFromNotebook(id)
            val local = notesRepository.getStoredNotes(id)
            val deleted = local.filterNot { localId -> notesRemote.any { it.id == localId.id } }
            notesRepository.deleteLocally(deleted)
        }


    }

}