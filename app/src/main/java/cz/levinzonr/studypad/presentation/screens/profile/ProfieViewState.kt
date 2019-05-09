package cz.levinzonr.studypad.presentation.screens.profile

import cz.levinzonr.studypad.domain.models.University

data class ProfieViewState(
    val username: String,
    val university: University?,
    val notificationsCount: Int,
    val imageUrl: String?
)