package cz.levinzonr.studypad.injection

import cz.levinzonr.studypad.domain.repository.NoteRestRepository
import cz.levinzonr.studypad.domain.repository.NotebookRepository
import cz.levinzonr.studypad.domain.repository.NotebookRestRepository
import cz.levinzonr.studypad.domain.repository.NotesRepository
import cz.levinzonr.studypad.storage.TokenRepository
import cz.levinzonr.studypad.storage.TokenRepositoryImpl
import org.koin.dsl.module.module


val repositoryModule = module {
    single<NotesRepository> { NoteRestRepository(get()) }

    single<NotebookRepository> { NotebookRestRepository(get()) }

    single<TokenRepository> {  TokenRepositoryImpl(get()) }
}

