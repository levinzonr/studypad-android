package cz.levinzonr.studypad.presentation.screens.selectors.university

import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.domain.models.ViewError
import cz.levinzonr.studypad.presentation.events.Event


data class UniversitySelectorViewState(
    val empty: Boolean = true,
    val universities: List<University> = listOf()
)