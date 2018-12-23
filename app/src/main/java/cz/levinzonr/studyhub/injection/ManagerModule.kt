package cz.levinzonr.studyhub.injection

import cz.levinzonr.studyhub.domain.managers.UserManager
import cz.levinzonr.studyhub.domain.managers.UserManagerImpl
import org.koin.dsl.module.module


val managerModule = module {

    single<UserManager> {  UserManagerImpl(get()) }
}