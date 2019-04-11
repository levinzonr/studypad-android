package cz.levinzonr.studypad.presentation.base

import cz.levinzonr.studypad.notifications.NotificationPayload
import cz.levinzonr.studypad.presentation.screens.notifications.NotificationType

interface NotificationHandler {
    fun handleNotification(type: NotificationType, notificationPayload: NotificationPayload)
}