package cz.levinzonr.studypad.domain.models

import java.lang.Exception

sealed class AuthenticationResult {

    data class Success(val userProfile: UserProfile) : AuthenticationResult()
    object InvalidCredentialsError : AuthenticationResult()
    object UnknownError: AuthenticationResult()
    data class Error(val exception: Exception): AuthenticationResult()

}