package cz.levinzonr.studyhub.presentation.screens.library

import androidx.lifecycle.ViewModel;
import cz.levinzonr.studyhub.domain.repository.MockNotesRepository

class NotesListViewModel : ViewModel() {
    val dataSource = MockNotesRepository().getNotesFromNotebook(1)
}
