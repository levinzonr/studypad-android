package cz.levinzonr.studypad.presentation.screens.settings

import cz.levinzonr.studypad.domain.models.Locale

data class SettingsViewState(
    val isNotificationsEnabled: Boolean = true,
    val language: Locale? = null
)