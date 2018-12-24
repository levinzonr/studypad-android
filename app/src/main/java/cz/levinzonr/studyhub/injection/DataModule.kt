package cz.levinzonr.studyhub.injection

import cz.levinzonr.studyhub.domain.repository.NoteRestRepository
import cz.levinzonr.studyhub.domain.repository.NotebookRepository
import cz.levinzonr.studyhub.domain.repository.NotebookRestRepository
import cz.levinzonr.studyhub.domain.repository.NotesRepository
import cz.levinzonr.studyhub.storage.TokenRepository
import cz.levinzonr.studyhub.storage.TokenRepositoryImpl
import org.koin.dsl.module.module


val repositoryModule = module {
    single<NotesRepository> { NoteRestRepository(get()) }

    single<NotebookRepository> { NotebookRestRepository(get()) }

    single<TokenRepository> {  TokenRepositoryImpl(get()) }
}

