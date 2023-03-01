package com.example.scrutinizing_the_service.notifs

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.scrutinizing_the_service.R

class ProgressNotificationBuilder(
    val context: Context
): NotificationBuilder {

    companion object {
        const val PROGRESS_CHANNEL_ID = "PROGRESS_CHANNEL_ID"
        const val PROGRESS_CHANNEL_IDENTIFIER = "PROGRESS_CHANNEL_IDENTIFIER"
        const val PROGRESS_BAR_MAX_VALUE = 100
        const val INITIAL_PROGRESS = 0
        const val PROGRESS_INTERVAL = 10
    }

    private val notificationManager by lazy {
        NotificationManagerCompat.from(context)
    }

    //TODO Notice this variables role
    private var notificationId = 0
    private var currentProgress = INITIAL_PROGRESS

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createNotification(context: Context) {
        val areNotificationsEnabled = notificationManager.areNotificationsEnabled()
        if(!areNotificationsEnabled) {
            redirectToSettings()
            return
        }
        val notification = NotificationCompat.Builder(context, PROGRESS_CHANNEL_ID)
            .setSmallIcon(R.drawable.placeholder)
            .setContentTitle("Download")
            .setContentText("Something download in progress")
            .setOngoing(true)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setProgress(PROGRESS_BAR_MAX_VALUE, currentProgress, false)

        if(currentProgress > PROGRESS_BAR_MAX_VALUE) {
            notification.setContentTitle("Download finish")
                .setContentText("")
                .setProgress(0, 0, false)
                .setOngoing(false)
        } else {
            currentProgress += PROGRESS_INTERVAL
        }

        /**
         * Every time we are creating a new notification.
         * But if the id is same as previous notifcation then it will
         * update the existing one so that we can update the existing notification
         * with the latest data.
         * But see below. For every notification, we are incrementing the id.
         * So each notification will be created and they will be distinguishable by their id
         */
        notificationManager.notify(notificationId, notification.build())

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createChannel(context: Context) {
        val channel1 = NotificationChannel(
            PROGRESS_CHANNEL_ID,
            PROGRESS_CHANNEL_IDENTIFIER,
            NotificationManager.IMPORTANCE_HIGH
        )
        channel1.description = "This is First Notification Channel description"
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel1)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun redirectToSettings() {
        Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            context.startActivity(this)
        }
    }


}