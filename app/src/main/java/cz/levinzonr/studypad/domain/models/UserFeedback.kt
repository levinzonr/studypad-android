package cz.levinzonr.studypad.domain.models

data class UserFeedback(
    val appVersionName: String,
    val appVersionCode: Int,
    val androidVersion: String,
    val device: String,
    val feedback: String
)