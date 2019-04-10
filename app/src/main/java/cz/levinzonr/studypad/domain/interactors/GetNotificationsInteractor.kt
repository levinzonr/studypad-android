package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.models.Notification
import cz.levinzonr.studypad.rest.Api

class GetNotificationsInteractor(private val api: Api) : BaseInteractor<List<Notification>>() {

    override suspend fun executeOnBackground(): List<Notification> {
        return api.getLatestNotifications().await().map {
            Notification(it.id.toString(), it.type, it.body, it.createdAt)
        }
    }
}