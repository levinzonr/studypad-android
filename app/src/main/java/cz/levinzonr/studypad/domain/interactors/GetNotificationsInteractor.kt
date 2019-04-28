package cz.levinzonr.studypad.domain.interactors

import cz.levinzonr.studypad.domain.models.Notification
import cz.levinzonr.studypad.presentation.screens.notifications.NotificationType
import cz.levinzonr.studypad.rest.Api

class GetNotificationsInteractor(private val api: Api) : BaseInteractor<List<Notification>>() {

    override suspend fun executeOnBackground(): List<Notification> {
        return api.getLatestNotificationsAsync().await().map {
            Notification(it.id, NotificationType.valueOf(it.type.capitalize()), it.read, it.notebookInfo.notebookId, it.body, it.createdAt, it.notebookInfo.notebookName, it.userInfo?.authorName)
        }
    }
}