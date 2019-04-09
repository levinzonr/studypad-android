package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.models.Notification

class GetNotificationsInteractor : BaseInteractor<List<Notification>>() {

    override suspend fun executeOnBackground(): List<Notification> {
        return List(2) {
            if (it == 0) Notification("$it", "update")
            else Notification("$it", "comment")
        }
    }
}