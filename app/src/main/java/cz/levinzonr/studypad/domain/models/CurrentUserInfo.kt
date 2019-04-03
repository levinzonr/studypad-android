package cz.levinzonr.studypad.domain.models

data class CurrentUserInfo(
    val id: String,
    val displayName: String,
    val university: University?,
    val chosenLocale: Locale)