package cz.levinzonr.studypad.presentation.screens

import androidx.navigation.NavDirections


sealed class NavigationEvent {

    data class NavigateTo(val directions: NavDirections) : NavigationEvent()
    data class ChangeFlow(val flow: Flow) : NavigationEvent()
    object NavigateBack: NavigationEvent()
}


enum class Flow {
    ONBOARDING, MAIN
}
