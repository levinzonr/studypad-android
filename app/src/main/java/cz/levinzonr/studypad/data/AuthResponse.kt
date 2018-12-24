package cz.levinzonr.studypad.data

import com.google.gson.annotations.SerializedName

class AuthResponse(
    @SerializedName("access_token") val token: String
)