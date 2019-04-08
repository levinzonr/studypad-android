 package cz.levinzonr.studypad.injection

import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.presentation.screens.library.notebooks.NotebookListViewModel
import cz.levinzonr.studypad.presentation.screens.library.notes.NoteDetailModels
import cz.levinzonr.studypad.presentation.screens.library.notes.NoteDetailViewModel
import cz.levinzonr.studypad.presentation.screens.library.notes.NotesListViewModel
import cz.levinzonr.studypad.presentation.screens.library.publish.PublishNotebookViewModel
import cz.levinzonr.studypad.presentation.screens.library.publish.TagSearchViewModel
import cz.levinzonr.studypad.presentation.screens.library.publish.TopicSearchViewModel
import cz.levinzonr.studypad.presentation.screens.library.publish.LanguageSelectorViewModel
import cz.levinzonr.studypad.presentation.screens.onboarding.login.LoginViewModel
import cz.levinzonr.studypad.presentation.screens.onboarding.signup.SignupViewModel
import cz.levinzonr.studypad.presentation.screens.onboarding.signup.UniversitySelectorViewModel
import cz.levinzonr.studypad.presentation.screens.profile.ProfileViewModel
import cz.levinzonr.studypad.presentation.screens.sharinghub.details.NotebookSuggestionsViewModel
import cz.levinzonr.studypad.presentation.screens.sharinghub.comments.PublishedNotebookCommentsViewModel
import cz.levinzonr.studypad.presentation.screens.sharinghub.details.PublishedNotebookDetailViewModel
import cz.levinzonr.studypad.presentation.screens.sharinghub.details.PublishedNotesListViewModel
import cz.levinzonr.studypad.presentation.screens.sharinghub.feed.SharingHubViewModel
import cz.levinzonr.studypad.presentation.screens.sharinghub.search.NotebookSearchModels
import cz.levinzonr.studypad.presentation.screens.sharinghub.search.NotebooksSearchViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {

    viewModel  { NotebookListViewModel(get(), get(), get(), get(), get(), get(), get()) }

    viewModel { (id: String) -> NotesListViewModel(id, get(), get()) }

    viewModel { LoginViewModel(get(), get(), get(), get()) }

    viewModel { ProfileViewModel(get(), get(), get()) }

    viewModel { SignupViewModel(get(), get(), get()) }

    viewModel { (mode: NoteDetailModels.NoteViewMode) -> NoteDetailViewModel(mode, get(), get(), get(), get()) }

    viewModel { (notebook: Notebook) -> PublishNotebookViewModel(notebook, get(), get(), get()) }

    viewModel { SharingHubViewModel(get()) }

    viewModel { TagSearchViewModel(get()) }

    viewModel { TopicSearchViewModel(get()) }

    viewModel { (id: String) ->
        PublishedNotebookDetailViewModel(
            id,
            get(),
            get(),
            get(),
            get()
        )
    }

    viewModel { (id: String) ->
        PublishedNotebookCommentsViewModel(
            id,
            get(),
            get(),
            get(),
            get()
        )
    }

    viewModel { PublishedNotesListViewModel() }

    viewModel { NotebookSuggestionsViewModel() }

    viewModel { LanguageSelectorViewModel(get()) }

    viewModel { UniversitySelectorViewModel(get()) }

    viewModel { (searchState: NotebookSearchModels.SearchState?) -> NotebooksSearchViewModel(searchState, get()) }

}