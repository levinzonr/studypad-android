package cz.levinzonr.studypad.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.repository.FirebaseTokenRepository
import cz.levinzonr.studypad.fromJson
import cz.levinzonr.studypad.presentation.screens.MainActivity
import org.koin.android.ext.android.inject
import timber.log.Timber

class StudyPadNotificationsService : FirebaseMessagingService() {

    companion object {
        private const val CHANNEL_ID = "studypad::notifications"
    }


    private val tokenRepository: FirebaseTokenRepository by inject()
    private val gson: Gson by inject()

    private val userManager: UserManager by inject()

    override fun onMessageReceived(message: RemoteMessage?) {
        super.onMessageReceived(message)
        message ?: return
        Timber.d("Message Receivde: $message")

        createNotificationChannel()

        val notificationsEnabled = userManager.getPreferences().isNotificationsEnabled
        Timber.d("Notifications enabled: $notificationsEnabled")
        val intent = Intent(IntentActions.NOTIFICATION)
        val payload = extractPayload(message)
        intent.putExtra("data", payload)

        val schedyked = LocalBroadcastManager.getInstance(this).sendBroadcast(intent)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setTitle(this, payload)
            .setMessage(this, payload)
            .setContentIntent(getPendingIntent(message))
            .setColor(ContextCompat.getColor(this, R.color.blue))
            .setSmallIcon(R.drawable.ic_notification)
            .setChannelId(CHANNEL_ID)
            .build()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (!schedyked && notificationsEnabled) manager.notify(code(payload?.type ?: ""), notification)
    }


    private fun getPendingIntent(message: RemoteMessage): PendingIntent? {
        val notification = extractPayload(message) ?: return null
        val intent = Intent(this, MainActivity::class.java).apply {
            action = ""
            putExtra("payload", notification)
        }
        return PendingIntent.getActivity(this, code(notification.type), intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun extractPayload(message: RemoteMessage) : NotificationPayload? {
        return message.data.get("payload")
            .also { Timber.d("Payload: $it") }
            ?.let { gson.fromJson(it) }
    }

    private fun code(string: String): Int {
        return when (string) {
            "comment" -> 1
            "suggestion" -> 2
            "update" -> 3
            else -> 4
        }
    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Timber.d("New Tokent: $token")
        token?.let(tokenRepository::putToken)
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "StudyPad notifications"
            val descriptionText = "Notifications about latest activities"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

}