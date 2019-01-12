 package cz.levinzonr.studypad.injection

import cz.levinzonr.studypad.domain.interactors.GetPublishedNotebookDetail
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.presentation.screens.library.notebooks.NotebookListViewModel
import cz.levinzonr.studypad.presentation.screens.library.notes.NoteDetailViewModel
import cz.levinzonr.studypad.presentation.screens.library.notes.NotesListViewModel
import cz.levinzonr.studypad.presentation.screens.library.publish.PublishNotebookViewModel
import cz.levinzonr.studypad.presentation.screens.library.publish.TagSearchDialog
import cz.levinzonr.studypad.presentation.screens.library.publish.TagSearchViewModel
import cz.levinzonr.studypad.presentation.screens.library.publish.TopicSearchViewModel
import cz.levinzonr.studypad.presentation.screens.onboarding.login.LoginViewModel
import cz.levinzonr.studypad.presentation.screens.onboarding.signup.SignupViewModel
import cz.levinzonr.studypad.presentation.screens.profile.ProfileViewModel
import cz.levinzonr.studypad.presentation.screens.sharedbooks.PublishedNotebookDetailViewModel
import cz.levinzonr.studypad.presentation.screens.sharedbooks.ShareHubViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {

    viewModel  { NotebookListViewModel(get(), get(), get(), get(), get()) }

    viewModel { (id: Long) -> NotesListViewModel(id, get(), get()) }

    viewModel { LoginViewModel(get(), get(), get()) }

    viewModel { ProfileViewModel(get(), get()) }

    viewModel { SignupViewModel(get(), get(), get()) }

    viewModel { (note: Note?) -> NoteDetailViewModel(note, get(), get(), get()) }

    viewModel { (notebook: Notebook) -> PublishNotebookViewModel(notebook, get(), get(), get()) }

    viewModel { ShareHubViewModel(get()) }

    viewModel { TagSearchViewModel(get()) }

    viewModel { TopicSearchViewModel(get()) }

    viewModel { (id: String) -> PublishedNotebookDetailViewModel(id, get()) }
}