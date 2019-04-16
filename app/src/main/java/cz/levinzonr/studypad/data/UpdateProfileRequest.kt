package cz.levinzonr.studypad.data

data class UpdateProfileRequest(
    val universityId: Long?,
    val displayName: String? = null
)