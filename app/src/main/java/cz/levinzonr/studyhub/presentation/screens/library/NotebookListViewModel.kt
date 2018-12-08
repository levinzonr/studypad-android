package cz.levinzonr.studyhub.presentation.screens.library

import androidx.lifecycle.ViewModel;
import cz.levinzonr.studyhub.domain.repository.MockNotebookRepository

class NotebookListViewModel : ViewModel() {

    val dataSource = MockNotebookRepository().getNotebooks()


}
