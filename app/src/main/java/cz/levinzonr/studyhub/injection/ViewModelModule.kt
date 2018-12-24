package cz.levinzonr.studyhub.injection

import cz.levinzonr.studyhub.presentation.screens.library.NotebookListViewModel
import cz.levinzonr.studyhub.presentation.screens.library.NotesListViewModel
import cz.levinzonr.studyhub.presentation.screens.onboarding.LoginViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {

    viewModel  { NotebookListViewModel(get(), get()) }

    viewModel { (id: Long) -> NotesListViewModel(id, get()) }

    viewModel { LoginViewModel(get(), get(), get())}
}