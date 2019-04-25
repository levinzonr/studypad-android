package cz.levinzonr.studypad.injection

import cz.levinzonr.studypad.presentation.adapters.*
import cz.levinzonr.studypad.presentation.screens.challenges.flashcards.FlashcardChallengeAdapter
import cz.levinzonr.studypad.presentation.screens.challenges.learning.LearningChallengeAdapter
import cz.levinzonr.studypad.presentation.screens.library.notebooks.NotebooksAdapter
import cz.levinzonr.studypad.presentation.screens.library.notes.NotesAdapter
import cz.levinzonr.studypad.presentation.screens.library.publish.LanguagesAdapter
import cz.levinzonr.studypad.presentation.screens.library.publish.TopicsAdapter
import cz.levinzonr.studypad.presentation.screens.notifications.NotificationsAdapter
import cz.levinzonr.studypad.presentation.screens.onboarding.signup.UniversityAdapter
import cz.levinzonr.studypad.presentation.screens.selectors.TopicsSelectionAdapter
import cz.levinzonr.studypad.presentation.screens.sharinghub.comments.CommentsAdapter
import cz.levinzonr.studypad.presentation.screens.sharinghub.feed.PublishedNotebooksAdapter
import cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.SuggestionsAdapter
import org.koin.dsl.module.module


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

    factory { (listener:  LearningChallengeAdapter.LearningChallengeItemListener) -> LearningChallengeAdapter(listener) }
}