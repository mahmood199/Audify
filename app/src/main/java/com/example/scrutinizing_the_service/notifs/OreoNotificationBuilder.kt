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

class OreoNotificationBuilder(context: Context) : NotificationBuilder {

    companion object {
        const val CHANNEL_1_ID = "CHANNEL_1_ID"
        const val CHANNEL_2_ID = "CHANNEL_2_ID"
    }

    private val notificationManager by lazy {
        NotificationManagerCompat.from(context)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createNotification(context: Context) {
        val notification = Notification.Builder(context, CHANNEL_1_ID)
            .setSmallIcon(R.drawable.placeholder)
            .setContentTitle("My First Notification Title")
            .setContentText("First Notification Description")
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build()
        notificationManager.notify(1, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createChannel(context: Context) {
        val channel1 = NotificationChannel(
            CHANNEL_1_ID,
            "some_identifier_1",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel1.description = "This is First Notification Channel description"

        val channel2 = NotificationChannel(
            CHANNEL_2_ID,
            "some_identifier_2",
            NotificationManager.IMPORTANCE_LOW
        )
        channel2.description = "This is Second Notification Channel description"

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel1)
        manager.createNotificationChannel(channel2)
/*
        manager.createNotificationChannels(mutableListOf(channel1, channel2))
*/
    }


}