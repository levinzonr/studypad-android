package cz.levinzonr.studypad.injection

import com.google.firebase.auth.FirebaseAuth
import cz.levinzonr.studypad.domain.managers.*
import cz.levinzonr.studypad.domain.managers.PrefManager
import cz.levinzonr.studypad.domain.managers.PrefManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module


val managerModule = module {

    single<UserManager> {  UserManagerImpl(get(), get(), get(), get(), get()) }

    single<PrefManager> { PrefManagerImpl(androidContext()) }

    single { FirebaseAuth.getInstance() }

    single <SearchManager> { SearchManagerImpl(get(), get()) }

    single <TranslationManager> { TranslationManagerImpl(androidContext()) }
}