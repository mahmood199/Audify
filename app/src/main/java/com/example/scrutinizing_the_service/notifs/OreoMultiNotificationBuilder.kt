package com.example.scrutinizing_the_service.notifs

import android.annotation.SuppressLint
import android.app.Notification
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

class OreoMultiNotificationBuilder(
    private val context: Context
) : NotificationBuilder {

    companion object {
        const val GENERIC_CHANNEL = "CHANNEL_ID_"
    }

    private val notificationManager by lazy {
        NotificationManagerCompat.from(context)
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun createNotification(context: Context) {
        for (it in 1..5) {
            val notification = Notification.Builder(context, "${GENERIC_CHANNEL}$it")
                .setSmallIcon(R.drawable.placeholder)
                .setContentTitle("My $it Notification Title")
                .setContentText("$it Notification Description")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build()
            notificationManager.notify(it, notification)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createChannel(context: Context) {
        createChannelInLoop()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannelInLoop() {
        val channels = mutableListOf<NotificationChannel>()
        for (it in 1..5) {
            val channel = NotificationChannel(
                "${GENERIC_CHANNEL}$it",
                "some_identifier_$it",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "This is $it channel description"
            channels.add(channel)
        }
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannels(channels)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun redirectToSettings() {
        Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            context.startActivity(this)
        }
    }

}