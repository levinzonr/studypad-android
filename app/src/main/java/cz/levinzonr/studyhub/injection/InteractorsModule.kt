package cz.levinzonr.studyhub.injection

import cz.levinzonr.studyhub.domain.interactors.*
import org.koin.dsl.module.module

val interacorModule = module {

    factory { GetNotebooksInteractor(get()) }

    factory { GetNotesInteractor(get()) }

    factory { LoginInteractor(get()) }

    factory { PostNotebookInteractor(get()) }

    factory { FacebookLoginInteractor(get()) }
}
