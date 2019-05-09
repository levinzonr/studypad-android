package cz.levinzonr.studypad.presentation.screens.onboarding.signup

import cz.levinzonr.studypad.presentation.events.Event

data class CredentiasViewState(
    val passwordInvalid: Event<String>? = null,
    val emailInvalid: Event<String>? = null
)