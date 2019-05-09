package cz.levinzonr.studypad.presentation.screens.onboarding.login

import cz.levinzonr.studypad.presentation.events.Event

data class LoginViewState(
    val emailEvent: Event<String>? = null,
    val pwdEvent: Event<String>? =  null
)