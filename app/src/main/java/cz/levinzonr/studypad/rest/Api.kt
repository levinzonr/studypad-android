package cz.levinzonr.studypad.rest

import cz.levinzonr.studypad.data.*
import cz.levinzonr.studypad.domain.models.*
import cz.levinzonr.studypad.notifications.NotificationPayload
import kotlinx.coroutines.Deferred
import retrofit2.http.*

private const val API = "api"
private const val AUTH = "/auth"


interface Api {

    @POST("$AUTH/login")
    fun loginAsync(@Query("token") token: String) : Deferred<UserProfile>


    @POST("$AUTH/register")
    fun createAccountAsync(@Body createAccountRequest: CreateAccountRequest) : Deferred<FirebaseAuthResponse>

    @GET("$API/university/find")
    fun getUniversitiesAsync(@Query("query") query: String) : Deferred<List<University>>

    @POST("$API/university/tmp")
    fun createUniversitySuggestionAsync(@Query("name") name: String) : Deferred<University>

    @POST("$API/users/me")
    fun updateUserAsync(@Body updateProfileRequest: UpdateProfileRequest) : Deferred<UserProfile>

    @GET("$API/users/me")
    fun getAuthenticatedUserProfileAsync() : Deferred<UserProfile>

    @POST("$API/config/feedback")
    fun leaveFeedbackAsync(@Body feedback: UserFeedback) : Deferred<Unit>

    //-----------------------------------------------------------------------------


    @GET("$API/users/me/notifications")
    fun getLatestNotificationsAsync() : Deferred<List<NotificationPayload>>


    @POST("$API/notifications/read")
    fun markNotificationsAsReadAsync(@Query("ids") list: List<Long>) : Deferred<Unit>


    @POST("$API/users/notifications/register")
    fun registerFirebaseTokenAsync(@Query("token") token: String) : Deferred<Unit>

    @POST("$API/users/notifications/unregister")
    fun unregisterFirebaseTokenAsync(@Query("token") token: String) : Deferred<Unit>

    //-----------------------------------------------------------------------------


    @GET("$API/notebooks")
    fun getNotebooksAsync() : Deferred<List<NotebooksResponse>>

    @POST("$API/notebooks")
    fun postNotebookAsync(@Body createNotebookRequest: CreateNotebookRequest) : Deferred<NotebooksResponse>

    @PATCH("$API/notebooks/{id}")
    fun updateNotebookAsync(@Path("id") id: String, @Body updateNotebookRequest: UpdateNotebookRequest) : Deferred<NotebooksResponse>

    @DELETE("$API/notebooks/{id}")
    fun deleteNotebookAsync(@Path("id") id: String) : Deferred<Unit>

    @POST("$API/notebooks/import")
    fun importPublisheNotebookAsync(@Query("id") id: String) : Deferred<NotebooksResponse>

    @POST("$API/notebooks/copy")
    fun importAsCopyAsync(@Query("id") id: String) : Deferred<NotebooksResponse>

    //-----------------------------------------------------------------------------

    @GET("$API/notebooks/{id}/notes")
    fun getNotesFromNotebookAsync(@Path("id") notebookId: String) : Deferred<List<Note>>

    @POST("$API/notes")
    fun createNoteAsync(@Body createNoteRequest: CreateNoteRequest) : Deferred<Note>

    @PATCH("$API/notes/{id}")
    fun updateNoteAsync(@Path("id") noteId: Long, @Body updateNoteRequest: UpdateNoteRequest) : Deferred<Note>

    @DELETE("$API/notes/{id}")
    fun deleteNoteAsync(@Path("id") noteId: Long) : Deferred<Unit>


    //-----------------------------------------------------------------------------

    @GET("$API/shared")
    fun getRelevantNotebooksAsync() : Deferred<List<SectionResponse>>

    @GET("$API/shared/{id}")
    fun getPublishedNotebookDetailAsync(@Path("id") id: String) : Deferred<PublishedNotebook.Detail>

    @GET("$API/shared/find")
    fun findNotebooksAsync(@Query("tags") tags: Set<String>,
                           @Query("topics") topic: List<Long>,
                           @Query("query") query: String,
                           @Query("university") universityId: Long?) : Deferred<List<PublishedNotebook.Feed>>

    @POST("$API/shared")
    fun publishNotebookAsync(@Body publishNotebookRequest: PublishNotebookRequest) : Deferred<PublishedNotebook.Feed>

    @PATCH("$API/shared/{id}")
    fun updatePublishedNotebookAsync(@Path("id") id: String, @Body payload: UpdatePublishedNotebookPayload) : Deferred<PublishedNotebook.Feed>

    @POST("$API/shared/quick")
    fun quickPublishAsync(@Query("id") notebookId: String) : Deferred<PublishedNotebook.Feed>

    @GET("$API/shared/tags")
    fun findTagsByNameAsync(@Query("name") name: String) : Deferred<List<String>>

    @GET("$API/config/topics")
    fun getTopicsAsync() : Deferred<List<Topic>>


    //-----------------------------------------------------------------------------

    @GET("$API/shared/{id}/suggestions")
    fun getPendingSuggestionsAsync(@Path("id") notebookId: String) : Deferred<List<PublishedNotebook.Modification>>

    @PATCH("$API/shared/{id}/suggestions/local")
    fun applyLocalChangesAsync(@Path("id") notebookId: String) : Deferred<PublishedNotebook.Detail>

    @POST("$API/shared/{id}/suggestions/review")
    fun reviewChangesAsync(@Path("id") notebookId: String, @Body submitReviewRequest: SubmitReviewRequest) : Deferred<PublishedNotebook.Detail>

    //-----------------------------------------------------------------------------

    @GET("$API/shared/{id}/comments")
    fun getSharedNotebookCommentsAsync(@Path("id") notebookId: String) : Deferred<List<PublishedNotebook.Comment>>

    @POST("$API/shared/{id}/comment")
    fun createCommentAsync(@Path("id") notebookId: String, @Query("comment") body: String) : Deferred<PublishedNotebook.Comment>

    @DELETE("$API/shared/comments/{id}")
    fun deleteCommentAsync(@Path("id") commentId: Long)  : Deferred<Unit>

    @POST("$API/shared/comments/{id}")
    fun editCommentAsync(@Path("id") commentId: Long, @Query("comment") body: String) : Deferred<PublishedNotebook.Comment>


}