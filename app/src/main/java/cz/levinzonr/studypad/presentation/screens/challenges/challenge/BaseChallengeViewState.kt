package cz.levinzonr.studypad.presentation.screens.challenges.challenge

import cz.levinzonr.studypad.presentation.events.Event
import cz.levinzonr.studypad.presentation.events.SingleLiveEvent
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengeStats

data class BaseChallengeViewState(
    val answeredCorrectly: Event<Int>? = null,
    val answeredWrong: Event<Int>? = null,
    val currentPosition: Int = 0,
    val repeatChallenge: SingleLiveEvent? = null,
    val completedEvent: Event<ChallengeStats>? = null
)