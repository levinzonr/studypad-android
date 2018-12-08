package cz.levinzonr.studyhub.presentation.screens

import androidx.lifecycle.ViewModel;
import cz.levinzonr.studyhub.domain.repository.MockNotebookRepository

class NotebookListViewModel : ViewModel() {

    val dataSource = MockNotebookRepository().getNotebooks()


}
