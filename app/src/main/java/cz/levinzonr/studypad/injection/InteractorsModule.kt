package cz.levinzonr.studypad.injection

import cz.levinzonr.studypad.domain.interactors.*
import cz.levinzonr.studypad.domain.interactors.comments.CreateCommentInteractor
import cz.levinzonr.studypad.domain.interactors.comments.DeleteCommentInteractor
import cz.levinzonr.studypad.domain.interactors.comments.EditCommentInteractor
import cz.levinzonr.studypad.domain.interactors.comments.GetCommentsInteractor
import cz.levinzonr.studypad.domain.interactors.keychain.*
import cz.levinzonr.studypad.domain.interactors.library.*
import cz.levinzonr.studypad.domain.interactors.sharinghub.*
import org.koin.dsl.module.module

/**
 * Module that contains interactors
 */
val interactorModule = module {

    factory { GetNotebooksInteractor(get()) }

    factory { GetNotesInteractor(get()) }

    factory { LoginInteractor(get()) }

    factory { PostNotebookInteractor(get()) }

    factory { FacebookLoginInteractor(get()) }

    factory { LogoutInteractor(get(), get()) }

    factory { GetUniversitiesInteractor(get() ) }

    factory { SignupInteractor(get()) }

    factory { UpdateNotebookInteractor(get()) }

    factory { DeleteNotebookInteractor(get()) }

    factory { DeleteNoteInteractor(get()) }

    factory { UpdateNoteInteractor(get()) }

    factory { PostNoteInteractor(get()) }

    factory { UpdateUserInteractor(get(), get()) }

    factory { GetUserProfileInteractor(get(), get()) }

    factory { PublishNotebookInteractor(get(), get()) }

    factory { GetRelevantNotebooks(get()) }

    factory { GetTagsByNameInteractor(get(), get()) }

    factory { GetTopicsInteractor(get()) }

    factory { GetPublishedNotebookDetail(get()) }

    factory { LibrarySyncInteractor(get(), get()) }

    factory { GoogleLoginInteractor(get()) }

    factory { EditCommentInteractor(get()) }

    factory { CreateCommentInteractor(get()) }

    factory { DeleteCommentInteractor(get()) }

    factory { GetCommentsInteractor(get()) }

    factory { QuiclPublishInteractor(get()) }

    factory { ImportPublishedNotebookInteractor(get()) }

    factory { GetNotebookVersionStateInteractor(get()) }

    factory { ApplyLocalChangesInteractor(get()) }

    factory { GetAvailableLanguagesInteractor(get()) }

    factory { GetNotificationsInteractor(get()) }

    factory { RefreshFirebaseTokenInteractor(get(), get()) }

    factory { MarkNotificationAsRead(get()) }

    factory { SubmitReviewInteractor(get()) }

    factory { SaveChallengeInteractor(get()) }

    factory { LeaveFeedbackInteractor(get()) }

    factory { GetPendingSuggestionsInteractor(get()) }

    factory { SuggestUniversityInteractor(get()) }

    factory { ImportCopyInteractor(get()) }
}
