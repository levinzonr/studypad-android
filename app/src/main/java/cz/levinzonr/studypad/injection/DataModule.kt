package cz.levinzonr.studypad.injection

import androidx.room.Room
import cz.levinzonr.studypad.domain.repository.*
import cz.levinzonr.studypad.storage.TokenRepository
import cz.levinzonr.studypad.storage.TokenRepositoryImpl
import cz.levinzonr.studypad.storage.UserProfileRepository
import cz.levinzonr.studypad.storage.UserProfileRepositoryImpl
import cz.levinzonr.studypad.storage.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module


val repositoryModule = module {
    single<NotesRepository> { NotesRepositoryImpl(get(), get()) }

    single<NotebookRepository> { NotebookRepositoryImpl(get(), get()) }

    single<ProfileRepository> { ProfileRestRepository(get()) }

    single<TokenRepository> {  TokenRepositoryImpl(get()) }

    single { Room.databaseBuilder(androidContext(), AppDatabase::class.java, "Database").build() }

    single<UserProfileRepository> { UserProfileRepositoryImpl(get(), get())  }

    single<PublishedNotebookRepository> { PublishedNotebookRepositoryImpl(get()) }

    single<CommentsRepository> { CommentsRepositoryImpl(get()) }

    single<TagsRepository> { TagsRepositoryImpl(get(), get()) }

    single<LocaleRepository> { LocaleRepositoryImpl(get(), get())}

    single <SearchHistoryRepository> { SearchHistoryRepositoryImpl(get()) }

    single<FirebaseTokenRepository> { FirebaseTokenRepositoryImpl(get()) }
}

