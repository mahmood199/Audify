package com.example.audify.reminder

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.skydiver.audify.R

class RunnerNotifier(
    private val notificationManager: NotificationManager,
    private val context: Context
) : Notifier(notificationManager) {

    override val notificationChannelId: String = "runner_channel_id"
    override val notificationChannelName: String = "Running Notification"
    override val notificationId: Int = 200

    override fun buildNotification(): Notification {
        return NotificationCompat.Builder(context, notificationChannelId)
            .setContentTitle(getNotificationTitle())
            .setContentText(getNotificationMessage())
            .setSmallIcon(R.mipmap.ic_app_launcher_v1)
            .build()
    }

    override fun getNotificationTitle(): String {
        return "Have you listened anything today?‍️"
    }

    override fun getNotificationMessage(): String {
        return "Listen to top trending songs for today"
    }
}