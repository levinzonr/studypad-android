package cz.levinzonr.studypad.domain.models

data class UserProfile(
    val firstName: String,
    val lastName: String,
    val university: University?,
    val photoUrl: String?
)