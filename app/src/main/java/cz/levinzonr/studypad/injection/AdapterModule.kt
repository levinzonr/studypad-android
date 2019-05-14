package cz.levinzonr.studypad.injection

import cz.levinzonr.studypad.presentation.adapters.*
import cz.levinzonr.studypad.presentation.screens.challenges.flashcards.FlashcardChallengeAdapter
import cz.levinzonr.studypad.presentation.screens.library.notebooks.NotebooksAdapter
import cz.levinzonr.studypad.presentation.screens.library.notes.NotesAdapter
import cz.levinzonr.studypad.presentation.screens.selectors.language.LanguagesAdapter
import cz.levinzonr.studypad.presentation.screens.selectors.topic.TopicsAdapter
import cz.levinzonr.studypad.presentation.screens.notifications.NotificationsAdapter
import cz.levinzonr.studypad.presentation.screens.selectors.university.UniversityAdapter
import cz.levinzonr.studypad.presentation.screens.selectors.topic.TopicsSelectionAdapter
import cz.levinzonr.studypad.presentation.screens.sharinghub.comments.CommentsAdapter
import cz.levinzonr.studypad.presentation.screens.sharinghub.feed.PublishedNotebooksAdapter
import cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.SuggestionsAdapter
import org.koin.dsl.module.module


/**
 * Koin module that contains RecyclerView adapters
 */
val adapterModule = module {


    factory { UniversityAdapter() }

    factory { NotesAdapter() }

    factory { NotebooksAdapter() }

    factory { PublishedNotebooksAdapter() }

    factory { (listner: TopicsAdapter.TopicListener) ->
        TopicsAdapter(
            listner
        )
    }

    factory { (listener: CommentsAdapter.CommentsItemListener,authorId: String?) ->
        CommentsAdapter(
            listener,
            authorId
        )
    }

    factory { SuggestionsAdapter() }

    factory { (listener: LanguagesAdapter.LanguageItemListener) ->
        LanguagesAdapter(
            listener
        )
    }

    factory { (listener: SearchEntryAdapter.SearchEntriesListener) -> SearchEntryAdapter(listener) }

    factory { (listener: NotificationsAdapter.NotificationItemsListener) ->
        NotificationsAdapter(
            listener
        )
    }

    factory { (listener: TopicsSelectionAdapter.TopicSelectionListener) ->
        TopicsSelectionAdapter(
            listener
        )
    }

    factory { (listenr: FlashcardChallengeAdapter.LearningChallengeAdapterListener) -> FlashcardChallengeAdapter(listener = listenr) }

}