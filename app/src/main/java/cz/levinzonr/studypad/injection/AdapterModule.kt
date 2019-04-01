package cz.levinzonr.studypad.injection

import cz.levinzonr.studypad.presentation.adapters.*
import org.koin.dsl.module.module


val adapterModule = module {


    factory { UniversityAdapter() }

    factory { NotesAdapter() }

    factory { NotebooksAdapter() }

    factory { PublishedNotebooksAdapter() }

    factory { (listner: TopicsAdapter.TopicListener) -> TopicsAdapter(listner) }

    factory { (listener: CommentsAdapter.CommentsItemListener,authorId: String?) -> CommentsAdapter(listener, authorId) }

    factory { SuggestionsAdapter() }
}