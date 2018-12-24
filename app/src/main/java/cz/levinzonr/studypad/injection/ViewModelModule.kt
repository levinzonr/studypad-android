package cz.levinzonr.studypad.injection

import cz.levinzonr.studypad.presentation.screens.library.notebooks.NotebookListViewModel
import cz.levinzonr.studypad.presentation.screens.library.notes.NotesListViewModel
import cz.levinzonr.studypad.presentation.screens.onboarding.login.LoginViewModel
import cz.levinzonr.studypad.presentation.screens.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {

    viewModel  { NotebookListViewModel(get(), get()) }

    viewModel { (id: Long) -> NotesListViewModel(id, get()) }

    viewModel { LoginViewModel(get(), get(), get()) }

    viewModel { ProfileViewModel(get()) }
}