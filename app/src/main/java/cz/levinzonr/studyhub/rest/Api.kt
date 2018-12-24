package cz.levinzonr.studyhub.rest

import cz.levinzonr.studyhub.data.AuthResponse
import cz.levinzonr.studyhub.data.CreateNotebookRequest
import cz.levinzonr.studyhub.data.EmailLoginRequest
import cz.levinzonr.studyhub.domain.Note
import cz.levinzonr.studyhub.domain.Notebook
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


    @GET("$API/notebooks/{id}/notes")
    fun getNotesFromNotebook(@Path("id") notebookId: Long) : Deferred<List<Note>>

}