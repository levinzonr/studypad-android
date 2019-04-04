package cz.levinzonr.studypad.presentation.screens.onboarding.signup

import cz.levinzonr.studypad.domain.models.University


data class UniversitySelectorViewState(
    val empty: Boolean = true,
    val universities: List<University> = listOf()
)