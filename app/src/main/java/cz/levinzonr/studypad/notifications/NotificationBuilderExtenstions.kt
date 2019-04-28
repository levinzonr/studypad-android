package cz.levinzonr.studypad.notifications

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import cz.levinzonr.studypad.R


fun String.notificationType() : NotificationType {
    return NotificationType.from(this)
}

fun NotificationCompat.Builder.setTitle(context: Context, payload: NotificationPayload?) : NotificationCompat.Builder {
    return when(payload?.type?.notificationType() ?: return setContentTitle("")) {
        NotificationType.Comment -> setContentTitle(context.getString(R.string.notification_comment_title))
        NotificationType.Update -> setContentTitle(context.getString(R.string.notification_update_title))
        NotificationType.Suggestion -> setContentTitle(context.getString(R.string.notification_suggestion_title))
    }
}

fun NotificationCompat.Builder.setMessage(context: Context, payload: NotificationPayload?) : NotificationCompat.Builder {
    return when(payload?.type?.notificationType() ?: return setContentText("")) {
        NotificationType.Comment -> setContentText(context.getString(R.string.notification_comment_message, payload.userInfo?.authorName, payload.notebookInfo.notebookName))
        NotificationType.Update -> setContentText(context.getString(R.string.notification_update_message, payload.notebookInfo.notebookName))
        NotificationType.Suggestion -> setContentText(context.getString(R.string.notification_suggestion_message, payload.notebookInfo.notebookName))
    }

}