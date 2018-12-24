package cz.levinzonr.studypad.injection

import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.managers.UserManagerImpl
import cz.levinzonr.studypad.storage.PrefManager
import cz.levinzonr.studypad.storage.PrefManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module


val managerModule = module {

    single<UserManager> {  UserManagerImpl(get(), get()) }

    single<PrefManager> { PrefManagerImpl(androidContext()) }
}