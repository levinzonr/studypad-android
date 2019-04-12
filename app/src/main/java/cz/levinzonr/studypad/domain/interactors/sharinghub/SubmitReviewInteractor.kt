package cz.levinzonr.studypad.domain.interactors.sharinghub

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.SuggestionsModels

class SubmitReviewInteractor : BaseInputInteractor<SuggestionsModels.SubmitedReview, Unit>() {

    override suspend fun executeOnBackground(input: SuggestionsModels.SubmitedReview) {

    }
}