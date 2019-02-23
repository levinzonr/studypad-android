package cz.levinzonr.studypad.rest

import cz.levinzonr.studypad.data.*
import cz.levinzonr.studypad.domain.models.*
import kotlinx.coroutines.Deferred
import retrofit2.http.*

private const val API = "api"
private const val AUTH = "/auth"

interface Api {


    @POST("$AUTH/email")
    fun loginUsingEmail(@Body emailLoginRequest: EmailLoginRequest) : Deferred<AuthResponse>

    @POST("$AUTH/facebook")
    fun loginViaFacebook(@Body facebookLoginRequest: FacebookLoginRequest) : Deferred<AuthResponse>

    @POST("$AUTH/register")
    fun createAccount(@Body createAccountRequest: CreateAccountRequest) : Deferred<FirebaseResponse>

    @GET("$API/university/find")
    fun getUniversities(@Query("query") query: String) : Deferred<List<University>>

    @POST("$API/users/signup/finish")
    fun updateUniversity(@Body updateUniversityRequest: UpdateUniversityRequest) : Deferred<Any>

    @GET("$API/users/me")
    fun getAuthenticatedUserProfile() : Deferred<UserProfile>

    //-----------------------------------------------------------------------------


    @GET("$API/notebooks")
    fun getNotebooks() : Deferred<List<Notebook>>

    @POST("$API/notebooks")
    fun postNotebook(@Body createNotebookRequest: CreateNotebookRequest) : Deferred<Notebook>

    @PATCH("$API/notebooks/{id}")
    fun updateNotebook(@Path("id") id: Long, @Body updateNotebookRequest: UpdateNotebookRequest) : Deferred<Notebook>

    @DELETE("$API/notebooks/{id}")
    fun deleteNotebook(@Path("id") id: Long) : Deferred<Unit>


    //-----------------------------------------------------------------------------

    @GET("$API/notebooks/{id}/notes")
    fun getNotesFromNotebook(@Path("id") notebookId: Long) : Deferred<List<Note>>

    @POST("$API/notes")
    fun createNote(@Body createNoteRequest: CreateNoteRequest) : Deferred<Note>

    @PATCH("$API/notes/{id}")
    fun updateNote(@Path("id") noteId: Long, @Body updateNoteRequest: UpdateNoteRequest) : Deferred<Note>

    @DELETE("$API/notes/{id}")
    fun deleteNote(@Path("id") noteId: Long) : Deferred<Unit>


    //-----------------------------------------------------------------------------

    @GET("$API/shared")
    fun getRelevantNotebooks() : Deferred<List<PublishedNotebook.Feed>>

    @GET("$API/shared/{id}")
    fun getPublishedNotebookDetail(@Path("id") id: String) : Deferred<PublishedNotebook.Detail>

    @GET("$API/shared/find")
    fun findNotebooks(@Query("tags") tags: Set<String>, @Query("topic") topic: String) : Deferred<List<PublishedNotebook>>

    @POST("$API/shared")
    fun publishNotebook(@Body publishNotebookRequest: PublishNotebookRequest) : Deferred<PublishedNotebook.Feed>

    @GET("$API/shared/tags")
    fun getTags() : Deferred<List<String>>

    @GET("$API/shared/topic")
    fun getTopics() : Deferred<List<Topic>>


    //-----------------------------------------------------------------------------

    @GET("$API/shared/{id}/comments")
    fun getSharedNotebookComments() : Deferred<List<PublishedNotebook.Comment>>

    @POST("$API/shared/{id}/comments")
    fun createComment() : Deferred<PublishedNotebook.Comment>




}