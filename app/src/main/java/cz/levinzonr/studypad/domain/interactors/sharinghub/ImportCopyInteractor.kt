package cz.levinzonr.studypad.domain.interactors.sharinghub

import cz.levinzonr.studypad.data.NotebooksResponse
import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.domain.repository.NotebookRepository

class ImportCopyInteractor(private val notebookRepository: NotebookRepository) : BaseInputInteractor<String, Unit>() {

    override suspend fun executeOnBackground(input: String) {
         notebookRepository.importAsCopy(input)
    }
}