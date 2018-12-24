package cz.levinzonr.studyhub.injection

import cz.levinzonr.studyhub.domain.repository.MockNotebookRepository
import cz.levinzonr.studyhub.domain.repository.MockNotesRepository
import cz.levinzonr.studyhub.domain.repository.NotebookRepository
import cz.levinzonr.studyhub.domain.repository.NotesRepository
import cz.levinzonr.studyhub.storage.TokenRepository
import cz.levinzonr.studyhub.storage.TokenRepositoryImpl
import org.koin.dsl.module.module


val repositoryModule = module {
    single<NotesRepository> { MockNotesRepository() }

    single<NotebookRepository> { MockNotebookRepository() }

    single<TokenRepository> {  TokenRepositoryImpl(get()) }
}

