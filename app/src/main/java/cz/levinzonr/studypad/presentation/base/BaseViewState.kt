package cz.levinzonr.studypad.presentation.base

import cz.levinzonr.studypad.domain.models.ApplicationError
import cz.levinzonr.studypad.domain.models.ViewError
import cz.levinzonr.studypad.presentation.events.Event
import cz.levinzonr.studypad.presentation.events.SingleLiveEvent

data class BaseViewState(
    val isLoading: Boolean = false,
    val networkError: SingleLiveEvent? = null,
    val error: Event<ViewError>? = null)