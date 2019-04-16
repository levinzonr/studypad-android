package cz.levinzonr.studypad.domain.interactors.sharinghub

import cz.levinzonr.studypad.data.SubmitReviewRequest
import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.SuggestionsModels
import cz.levinzonr.studypad.rest.Api

class SubmitReviewInteractor(private val api: Api) : BaseInputInteractor<SubmitReviewInteractor.Input, Unit>() {

    data class Input(val notebookId: String, val rejected: List<Long>, val aproved:List<Long>)

    override suspend fun executeOnBackground(input: Input) {
        api.reviewChangesAsync(input.notebookId, SubmitReviewRequest(input.aproved, input.rejected)).await()
    }
}