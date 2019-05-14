package cz.levinzonr.studypad.injection

import androidx.room.Room
import cz.levinzonr.studypad.domain.repository.*
import cz.levinzonr.studypad.rest.repository.*
import cz.levinzonr.studypad.storage.database.AppDatabase
import cz.levinzonr.studypad.storage.repository.LocaleRepositoryImpl
import cz.levinzonr.studypad.storage.repository.TokenRepositoryImpl
import cz.levinzonr.studypad.storage.repository.UserPreferencesRepositoryImpl
import cz.levinzonr.studypad.storage.repository.UserProfileRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module


/**
 * Module that contains all the repos used
 */
val repositoryModule = module {
    single<NotesRepository> { NotesRepositoryImpl(get(), get()) }

    single<NotebookRepository> { NotebookRepositoryImpl(get(), get()) }

    single<ProfileRepository> { ProfileRestRepository(get()) }

    single<TokenRepository> { TokenRepositoryImpl(get()) }

    single { Room.databaseBuilder(androidContext(), AppDatabase::class.java, "Database").build() }

    single<UserProfileRepository> { UserProfileRepositoryImpl(get(), get()) }

    single<PublishedNotebookRepository> { PublishedNotebookRepositoryImpl(get()) }

    single<CommentsRepository> { CommentsRepositoryImpl(get()) }

    single<TagsRepository> { TagsRepositoryImpl(get(), get()) }

    single<LocaleRepository> { LocaleRepositoryImpl(get(), get()) }

    single <SearchHistoryRepository> { SearchHistoryRepositoryImpl(get()) }

    single<FirebaseTokenRepository> { FirebaseTokenRepositoryImpl(get()) }

    single<UserPreferencesRepository> { UserPreferencesRepositoryImpl(get()) }

    single<ChallengesRepository> { ChallengesRepositoryImpl(get<AppDatabase>().challengesDao()) }

    single<KeychainRepository>{ KeychainRepositoryImpl(get(), get()) }
}

