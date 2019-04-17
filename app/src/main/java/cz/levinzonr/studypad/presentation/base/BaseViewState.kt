package cz.levinzonr.studypad.presentation.base

import cz.levinzonr.studypad.domain.models.ApplicationError
import cz.levinzonr.studypad.domain.models.ViewError
import cz.levinzonr.studypad.presentation.events.Event

data class BaseViewState(
    val isLoading: Boolean = false,
    val error: Event<ViewError>? = null)