package cz.levinzonr.studypad.injection

import cz.levinzonr.studypad.presentation.adapters.*
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengesModels
import cz.levinzonr.studypad.presentation.screens.challenges.flashcards.FlashcardChallengeAdapter
import cz.levinzonr.studypad.presentation.screens.challenges.learning.LearningChallengeAdapter
import org.koin.dsl.module.module


val adapterModule = module {


    factory { UniversityAdapter() }

    factory { NotesAdapter() }

    factory { NotebooksAdapter() }

    factory { PublishedNotebooksAdapter() }

    factory { (listner: TopicsAdapter.TopicListener) -> TopicsAdapter(listner) }

    factory { (listener: CommentsAdapter.CommentsItemListener,authorId: String?) -> CommentsAdapter(listener, authorId) }

    factory { SuggestionsAdapter() }

    factory { (listener: LanguagesAdapter.LanguageItemListener) -> LanguagesAdapter(listener) }

    factory { (listener: SearchEntryAdapter.SearchEntriesListener) -> SearchEntryAdapter(listener) }

    factory { (listener: NotificationsAdapter.NotificationItemsListener) -> NotificationsAdapter(listener ) }

    factory { (listener: TopicsSelectionAdapter.TopicSelectionListener) -> TopicsSelectionAdapter(listener) }

    factory { (listenr: FlashcardChallengeAdapter.LearningChallengeAdapterListener) -> FlashcardChallengeAdapter(listener = listenr) }

    factory { (listener:  LearningChallengeAdapter.LearningChallengeItemListener) -> LearningChallengeAdapter(listener) }
}