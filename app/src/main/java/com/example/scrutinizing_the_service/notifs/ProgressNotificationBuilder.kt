package com.example.scrutinizing_the_service.notifs

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
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
    }

    private val notificationManager by lazy {
        NotificationManagerCompat.from(context)
    }

    //TODO Notice this variables role
    private var notificationId = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createNotification(context: Context) {
        val notification = Notification.Builder(context, PROGRESS_CHANNEL_ID)
            .setSmallIcon(R.drawable.placeholder)
            .setContentTitle("My First Notification Title")
            .setContentText("First Notification Description")
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build()
        /**
         * Every time we are creating a new notification.
         * But if the id is same as previous notifcation then it will
         * update the existing one so that we can update the existing notification
         * with the latest data.
         * But see below. For every notification, we are incrementing the id.
         * So each notification will be created and they will be distinguishable by their id
         */
        notificationManager.notify(notificationId++, notification)
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


}