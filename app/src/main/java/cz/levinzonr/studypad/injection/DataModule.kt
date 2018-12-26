package cz.levinzonr.studypad.injection

import cz.levinzonr.studypad.domain.repository.*
import cz.levinzonr.studypad.storage.TokenRepository
import cz.levinzonr.studypad.storage.TokenRepositoryImpl
import org.koin.dsl.module.module


val repositoryModule = module {
    single<NotesRepository> { NoteRestRepository(get()) }

    single<NotebookRepository> { NotebookRestRepository(get()) }

    single<ProfileRepository> { ProfileRestRepository(get()) }

    single<TokenRepository> {  TokenRepositoryImpl(get()) }
}

