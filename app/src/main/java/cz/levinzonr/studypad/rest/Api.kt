package cz.levinzonr.studypad.rest

import cz.levinzonr.studypad.data.AuthResponse
import cz.levinzonr.studypad.data.CreateNotebookRequest
import cz.levinzonr.studypad.data.EmailLoginRequest
import cz.levinzonr.studypad.data.FacebookLoginRequest
import cz.levinzonr.studypad.domain.Note
import cz.levinzonr.studypad.domain.Notebook
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

private const val API = "api"
private const val AUTH = "/auth"

interface Api {


    @POST("$AUTH/email")
    fun loginUsingEmail(@Body emailLoginRequest: EmailLoginRequest) : Deferred<AuthResponse>

    @GET("$API/notebooks")
    fun getNotebooks() : Deferred<List<Notebook>>

    @POST("$API/notebooks")
    fun postNotebook(@Body createNotebookRequest: CreateNotebookRequest) : Deferred<Notebook>


    @POST("$AUTH/facebook")
    fun loginViaFacebook(@Body facebookLoginRequest: FacebookLoginRequest) : Deferred<AuthResponse>

    @GET("$API/notebooks/{id}/notes")
    fun getNotesFromNotebook(@Path("id") notebookId: Long) : Deferred<List<Note>>

}