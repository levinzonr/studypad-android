package cz.levinzonr.studypad.presentation.screens.about.feedback

import cz.levinzonr.studypad.presentation.events.SingleLiveEvent

data class SendFeedbackViewState(
    val sendButtonEnabled: Boolean = false,
    val feedbackSentEvent: SingleLiveEvent? = null
)