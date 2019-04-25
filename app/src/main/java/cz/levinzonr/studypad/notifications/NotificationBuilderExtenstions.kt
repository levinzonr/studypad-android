package cz.levinzonr.studypad.notifications

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat


fun String.notificationType() : NotificationType {
    return NotificationType.from(this)
}

fun NotificationCompat.Builder.setTitle(context: Context, payload: NotificationPayload?) : NotificationCompat.Builder {
    return when(payload?.type?.notificationType() ?: return setContentTitle("")) {
        NotificationType.Comment -> setContentTitle("New comment")
        NotificationType.Update -> setContentTitle("Notebook update")
        NotificationType.Suggestion -> setContentTitle("Notebook suggestion")
    }
}

fun NotificationCompat.Builder.setMessage(context: Context, payload: NotificationPayload?) : NotificationCompat.Builder {
    return when(payload?.type?.notificationType() ?: return setContentText("")) {
        NotificationType.Comment -> setContentText("New comment left on your ${payload.notebookInfo.notebookName} notebook")
        NotificationType.Update -> setContentText("New version of the ${payload.notebookInfo.notebookName} notebooks available")
        NotificationType.Suggestion -> setContentText("Your ${payload.notebookInfo.notebookName} notebook got a new suggestion")
    }

}