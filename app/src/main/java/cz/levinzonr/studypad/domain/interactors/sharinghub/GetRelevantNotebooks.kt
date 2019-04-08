package cz.levinzonr.studypad.domain.interactors.sharinghub

import cz.levinzonr.studypad.data.SectionResponse
import cz.levinzonr.studypad.domain.interactors.BaseInteractor
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.repository.PublishedNotebookRepository

class GetRelevantNotebooks(private val publishedNotebookRepository: PublishedNotebookRepository) : BaseInteractor<List<SectionResponse>>() {

    override suspend fun executeOnBackground(): List<SectionResponse> {
        return publishedNotebookRepository.getRelevantNotebooks()
    }

}