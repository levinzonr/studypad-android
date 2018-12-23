package cz.levinzonr.studyhub.rest.utils

import cz.levinzonr.studyhub.data.AuthResponse
import cz.levinzonr.studyhub.data.EmailLoginRequest
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

private const val API = "api"
private const val AUTH = "/auth"

interface Api {


    @POST("$AUTH/email")
    fun loginUsingEmail(@Body emailLoginRequest: EmailLoginRequest) : Deferred<AuthResponse>
}