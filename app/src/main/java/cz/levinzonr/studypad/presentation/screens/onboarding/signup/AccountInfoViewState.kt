package cz.levinzonr.studypad.presentation.screens.onboarding.signup

import cz.levinzonr.studypad.presentation.events.Event

data class AccountInfoViewState(
    val firstNameValid: Event<String>? = null,
    val lastNameValid: Event<String>? = null
)