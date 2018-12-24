package cz.levinzonr.studyhub.injection

import cz.levinzonr.studyhub.domain.managers.UserManager
import cz.levinzonr.studyhub.domain.managers.UserManagerImpl
import cz.levinzonr.studyhub.storage.PrefManager
import cz.levinzonr.studyhub.storage.PrefManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module


val managerModule = module {

    single<UserManager> {  UserManagerImpl(get(), get()) }

    single<PrefManager> { PrefManagerImpl(androidContext()) }
}