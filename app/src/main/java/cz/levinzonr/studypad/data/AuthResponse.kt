package cz.levinzonr.studypad.data

import com.google.gson.annotations.SerializedName
import cz.levinzonr.studypad.domain.models.UserProfile

class AuthResponse(
    @SerializedName("access_token") val token: String,
    val user: UserProfile
)

data  class FirebaseResponse(val token: String, val user: UserProfile)