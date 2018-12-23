package cz.levinzonr.studyhub.injection

import cz.levinzonr.studyhub.domain.interactors.GetNotebooksInteractor
import cz.levinzonr.studyhub.domain.interactors.GetNotesInteractor
import org.koin.dsl.module.module

val interacorModule = module {

    factory { GetNotebooksInteractor(get()) }

    factory { GetNotesInteractor(get()) }
}
