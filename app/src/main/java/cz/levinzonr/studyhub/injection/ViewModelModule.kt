package cz.levinzonr.studyhub.injection

import cz.levinzonr.studyhub.domain.GetNotebooksInteractor
import cz.levinzonr.studyhub.presentation.screens.library.NotebookListViewModel
import org.koin.androidx.viewmodel.experimental.builder.viewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {

    viewModel  { NotebookListViewModel(GetNotebooksInteractor()) }

}