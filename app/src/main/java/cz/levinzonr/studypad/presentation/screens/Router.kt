package cz.levinzonr.studypad.presentation.screens

import androidx.navigation.NavDirections


sealed class NavigationEvent {

    data class NavigateTo(val directions: NavDirections) : NavigationEvent()
    data class ChangeFlow(val flow: Flow) : NavigationEvent()
    object NavigateBack: NavigationEvent()
}


enum class Flow {
    ONBOARDING, MAIN
}





/*
private const val ARG_NOTEBOOK = "NOTEBOOK"
private const val ARG_NOTEBOOK_ID = "NOTEBOOK_ID"
private const val ARG_NOTE = "NOTE"
private const val ARG_PUBLISHED_NOTEBOOK = "PUBLISHED_NOTEBOOK"

fun Fragment.onNotebookSelected(notebook: Notebook) {
    view?.findNavController()?.navigate(R.id.action_notebookListFragment_to_notesListFragment,
        Bundle().apply { putParcelable(ARG_NOTEBOOK, notebook) }
    )
}

fun NotesListFragment.showNoteEdit(notebookId: String, note: Note?) {
    view?.findNavController()?.navigate(R.id.action_notesListFragment_to_editNoteFragment,
        Bundle().apply {
            putParcelable(ARG_NOTE, note)
            putString(ARG_NOTEBOOK_ID, notebookId)
        })
}

fun NoteDetailFragment.showNoteEdit(notebookId: Long, note: Note?) {
    view?.findNavController()?.navigate(R.id.action_noteDetailFragment_to_editNoteFragment,
        Bundle().apply {
            putParcelable(ARG_NOTE, note)
            putLong(ARG_NOTEBOOK_ID, notebookId)
        })
}


fun NotesListFragment.showNoteDetail(note: Note) {
    view?.findNavController()?.navigate(R.id.action_notesListFragment_to_noteDetailFragment,
        Bundle().apply { putParcelable(ARG_NOTE, note) })
}

val NotesListFragment.notebook: Notebook?
    get() = arguments?.getParcelable(ARG_NOTEBOOK)

val EditNoteFragment.note: Note?
    get() = arguments?.getParcelable(ARG_NOTE)

val NoteDetailFragment.note: Note
    get() = arguments?.getParcelable(ARG_NOTE)!!

val PublishNotebookFragment.notebook: Notebook
    get() = arguments?.getParcelable(ARG_NOTEBOOK)!!

val EditNoteFragment.notebookId: String
    get() = arguments?.getString(ARG_NOTEBOOK_ID)!!

fun Fragment.showMain() {
    startActivity(Intent(activity, MainActivity::class.java))
    activity?.finish()
}

fun Fragment.showAccountCreation() {
    view?.findNavController()?.navigate(R.id.action_loginFragment2_to_accountInfoFragment)
}

fun Fragment.showAccounCreationNextStep() {
    view?.findNavController()?.navigate(R.id.action_accountInfoFragment_to_credentialsInfoFragment)
}



fun Fragment.navigateBack() {
    view?.findNavController()?.popBackStack()
}

fun CredentialsInfoFragment.showAccountCreated() {
    view?.findNavController()?.navigate(R.id.action_credentialsInfoFragment_to_accountCreatedFragment)
}

fun AccountCreatedFragment.showUniversitySelector() {
    view?.findNavController()?.navigate(R.id.action_accountCreatedFragment_to_universitySelectorFragment)
}

fun LoginFragment.showUniversitySelector() {
    view?.findNavController()?.navigate(R.id.action_loginFragment2_to_accountCreatedFragment)
}

fun NotebookListFragment.showPublishNotebookView(notebook: Notebook) {
    view?.findNavController()?.navigate(R.id.action_notebookListFragment_to_publishNotebookFragment,
        Bundle().apply {
            putParcelable(ARG_NOTEBOOK, notebook)
        })
}

fun SharingHubFragment.showDetail(notebook: PublishedNotebook.Feed) {
    view?.findNavController()?.navigate(R.id.action_sharedFragment_to_publishedNotebookDetailFragment,
        Bundle().apply { putParcelable(ARG_PUBLISHED_NOTEBOOK, notebook) }
    )
}

val PublishedNotebookDetailFragment.feedItem: PublishedNotebook.Feed
    get() = arguments!!.getParcelable(ARG_PUBLISHED_NOTEBOOK) !!

fun NotebookListFragment.onShowPublishedNotebook(nb: PublishedNotebook.Feed) {
    view?.findNavController()?.navigate(R.id.action_notebookListFragment_to_publishedNotebookDetailFragment,
        Bundle().apply { putParcelable(ARG_PUBLISHED_NOTEBOOK, nb) })
} */
