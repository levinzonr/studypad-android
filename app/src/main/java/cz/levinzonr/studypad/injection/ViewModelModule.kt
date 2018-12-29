package cz.levinzonr.studypad.injection

import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.presentation.screens.library.notebooks.NotebookListViewModel
import cz.levinzonr.studypad.presentation.screens.library.notes.NoteDetailViewModel
import cz.levinzonr.studypad.presentation.screens.library.notes.NotesListViewModel
import cz.levinzonr.studypad.presentation.screens.onboarding.login.LoginViewModel
import cz.levinzonr.studypad.presentation.screens.onboarding.signup.SignupViewModel
import cz.levinzonr.studypad.presentation.screens.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {

    viewModel  { NotebookListViewModel(get(), get(), get(), get(), get()) }

    viewModel { (id: Long) -> NotesListViewModel(id, get(), get()) }

    viewModel { LoginViewModel(get(), get(), get()) }

    viewModel { ProfileViewModel(get(), get()) }

    viewModel { SignupViewModel(get(), get(), get()) }

    viewModel { (note: Note?) -> NoteDetailViewModel(note, get(), get(), get()) }
}