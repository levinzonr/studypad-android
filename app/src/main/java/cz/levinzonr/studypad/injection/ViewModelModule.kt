 package cz.levinzonr.studypad.injection

import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengesOverviewViewModel
import cz.levinzonr.studypad.presentation.screens.challenges.challenge.ChallengeViewModel
import cz.levinzonr.studypad.presentation.screens.challenges.setup.SetupChallengeViewModel
import cz.levinzonr.studypad.presentation.screens.challenges.setup.SetupChallengeViewState
import cz.levinzonr.studypad.presentation.screens.library.notebooks.NotebookListViewModel
import cz.levinzonr.studypad.presentation.screens.library.notes.NoteDetailModels
import cz.levinzonr.studypad.presentation.screens.library.notes.NoteDetailViewModel
import cz.levinzonr.studypad.presentation.screens.library.notes.NotesListViewModel
import cz.levinzonr.studypad.presentation.screens.library.publish.PublishNotebookViewModel
import cz.levinzonr.studypad.presentation.screens.library.publish.TagSearchViewModel
import cz.levinzonr.studypad.presentation.screens.library.publish.TopicSearchViewModel
import cz.levinzonr.studypad.presentation.screens.library.publish.LanguageSelectorViewModel
import cz.levinzonr.studypad.presentation.screens.notifications.NotificationsViewModel
import cz.levinzonr.studypad.presentation.screens.onboarding.login.LoginViewModel
import cz.levinzonr.studypad.presentation.screens.onboarding.signup.SignupViewModel
import cz.levinzonr.studypad.presentation.screens.onboarding.signup.UniversitySelectorViewModel
import cz.levinzonr.studypad.presentation.screens.profile.ProfileViewModel
import cz.levinzonr.studypad.presentation.screens.profile.edit.EditProfileViewModel
import cz.levinzonr.studypad.presentation.screens.settings.SettingsViewModel
import cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.NotebookSuggestionsViewModel
import cz.levinzonr.studypad.presentation.screens.sharinghub.comments.PublishedNotebookCommentsViewModel
import cz.levinzonr.studypad.presentation.screens.sharinghub.details.PublishedNotebookDetailViewModel
import cz.levinzonr.studypad.presentation.screens.sharinghub.details.PublishedNotesListViewModel
import cz.levinzonr.studypad.presentation.screens.sharinghub.feed.SharingHubViewModel
import cz.levinzonr.studypad.presentation.screens.sharinghub.search.NotebookSearchModels
import cz.levinzonr.studypad.presentation.screens.sharinghub.search.NotebooksSearchViewModel
import cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.ReviewSuggestionsViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {

    viewModel  { NotebookListViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }

    viewModel { (id: String) -> NotesListViewModel(id, get(), get()) }

    viewModel { LoginViewModel(get(), get(), get(), get()) }

    viewModel { ProfileViewModel(get(), get(), get()) }

    viewModel { SignupViewModel(get(), get(), get()) }

    viewModel { (mode: NoteDetailModels.NoteViewMode) -> NoteDetailViewModel(mode, get(), get(), get(), get()) }

    viewModel { (notebook: Notebook?, id: String?) -> PublishNotebookViewModel(notebook, id, get(),  get(), get(), get()) }


    viewModel { (isPreviewMode: Boolean) -> NotificationsViewModel(isPreviewMode, get(), get())}

    viewModel { SharingHubViewModel(get(), get()) }

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

    viewModel { (notes: Array<Note>, suggestions: Array<PublishedNotebook.Modification>) -> NotebookSuggestionsViewModel(suggestions.toList(), notes.toList()) }

    viewModel { LanguageSelectorViewModel(get()) }

    viewModel { UniversitySelectorViewModel(get()) }

    viewModel { (searchState: NotebookSearchModels.SearchState?) -> NotebooksSearchViewModel(searchState, get()) }

    viewModel { (list: Array<PublishedNotebook.Modification>, notes: Array<Note>) -> ReviewSuggestionsViewModel(notes.toList(), list.toList(), get()) }

    viewModel { EditProfileViewModel(get(), get()) }

    viewModel { SetupChallengeViewModel() }

    viewModel { SettingsViewModel(get(), get() )}

    viewModel { (setup: SetupChallengeViewState) -> ChallengeViewModel(get(), get(), setup) }

    viewModel { ChallengesOverviewViewModel(get()) }

}