package cz.levinzonr.studypad.injection

import cz.levinzonr.studypad.domain.interactors.*
import cz.levinzonr.studypad.domain.interactors.keychain.*
import cz.levinzonr.studypad.domain.interactors.library.*
import cz.levinzonr.studypad.domain.interactors.sharinghub.*
import org.koin.dsl.module.module

val interacorModule = module {

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

    factory { UpdateUserUniversityInteractor(get()) }

    factory { GetUserProfileInteractor(get()) }

    factory { PublishNotebookInteractor(get()) }

    factory { GetRelevantNotebooks(get()) }

    factory { GetTagsInteractor() }

    factory { GetTopicsInteractor(get()) }

    factory { GetPublishedNotebookDetail(get()) }

    factory { LibrarySyncInteractor(get(), get()) }

    factory { GoogleLoginInteractor(get()) }
}
