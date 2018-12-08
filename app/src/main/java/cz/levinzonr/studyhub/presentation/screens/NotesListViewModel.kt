package cz.levinzonr.studyhub.presentation.screens

import androidx.lifecycle.ViewModel;
import cz.levinzonr.studyhub.domain.repository.MockNotesRepository

class NotesListViewModel : ViewModel() {
    val dataSource = MockNotesRepository().getNotesFromNotebook(1)
}
