package cz.levinzonr.studypad.injection

import cz.levinzonr.studypad.domain.interactors.*
import org.koin.dsl.module.module

val interacorModule = module {

    factory { GetNotebooksInteractor(get()) }

    factory { GetNotesInteractor(get()) }

    factory { LoginInteractor(get()) }

    factory { PostNotebookInteractor(get()) }

    factory { FacebookLoginInteractor(get()) }
}
