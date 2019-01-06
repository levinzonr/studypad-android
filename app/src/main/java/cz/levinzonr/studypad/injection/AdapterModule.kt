package cz.levinzonr.studypad.injection

import cz.levinzonr.studypad.presentation.adapters.NotebooksAdapter
import cz.levinzonr.studypad.presentation.adapters.NotesAdapter
import cz.levinzonr.studypad.presentation.adapters.PublishedNotebooksAdapter
import cz.levinzonr.studypad.presentation.adapters.UniversityAdapter
import org.koin.dsl.module.module


val adapterModule = module {


    factory { UniversityAdapter() }

    factory { NotesAdapter() }

    factory { NotebooksAdapter() }

    factory { PublishedNotebooksAdapter() }
}