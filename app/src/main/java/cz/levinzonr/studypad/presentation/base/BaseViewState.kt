package cz.levinzonr.studypad.presentation.base

import cz.levinzonr.studypad.domain.models.ApplicationError
import cz.levinzonr.studypad.domain.models.ViewError
import cz.levinzonr.studypad.presentation.events.Event
import cz.levinzonr.studypad.presentation.events.SingleLiveEvent

/**
 * BaseViewState to reflect most common UI states
 * @param isLoading -- tells the if the loading state is active
 * @param networkError -- Single live event to handle network error
 * @param error -- single live event to handle error
 */
data class BaseViewState(
    val isLoading: Boolean = false,
    val networkError: SingleLiveEvent? = null,
    val error: Event<ViewError>? = null)