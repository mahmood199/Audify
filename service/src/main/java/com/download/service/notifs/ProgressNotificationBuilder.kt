package com.download.service.notifs

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.download.service.R

@RequiresApi(Build.VERSION_CODES.M)
class ProgressNotificationBuilder(
    val context: Context
) : NotificationBuilder {

    companion object {
        const val PROGRESS_CHANNEL_ID = "PROGRESS_CHANNEL_ID"
        const val PROGRESS_CHANNEL_IDENTIFIER = "PROGRESS_CHANNEL_IDENTIFIER"
        const val PROGRESS_BAR_MAX_VALUE = 100
        const val INITIAL_PROGRESS = 0
        const val PROGRESS_INTERVAL = 10
    }

    private val notificationManagerCompat by lazy {
        NotificationManagerCompat.from(context)
    }

    private val actualNotificationManager by lazy {
        context.getSystemService(NotificationManager::class.java)
    }

    //TODO Notice this variables role
    private var notificationId = 0
    private var currentProgress = INITIAL_PROGRESS

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun createNotification(context: Context) {
        val areNotificationsEnabled = notificationManagerCompat.areNotificationsEnabled()
        if (!areNotificationsEnabled) {
            redirectToSettings()
            return
        }
        if (!isChannelBlocked(PROGRESS_CHANNEL_ID)) {
            redirectToNotificationChannelSettings(PROGRESS_CHANNEL_ID)
            return
        }
        val notification = NotificationCompat.Builder(context, PROGRESS_CHANNEL_ID)
            .setSmallIcon(R.drawable.placeholder)
            .setContentTitle("Download")
            .setContentText("Something download in progress")
            .setOngoing(true)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setProgress(PROGRESS_BAR_MAX_VALUE, currentProgress, false)

        if (currentProgress > PROGRESS_BAR_MAX_VALUE) {
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
        notificationManagerCompat.notify(notificationId, notification.build())

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

    /**
     * Because this option is available on android O(API level 26) and above.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun isChannelBlocked(channelId: String): Boolean {
        val channel = actualNotificationManager.getNotificationChannel(channelId)
        return channel != null && channel.importance != NotificationManager.IMPORTANCE_NONE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun redirectToNotificationChannelSettings(channelId: String) {
        Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS).apply {
            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            putExtra(Settings.EXTRA_CHANNEL_ID, channelId)
            context.startActivity(this)
        }
    }

    /**
     *  Deletes the channel or channel group that we created already
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteChannel(channelId: String) {
        actualNotificationManager.deleteNotificationChannel(channelId)
        // TODO - The below line of code deletes all the channels in a particular group.
        // actualNotificationManager.deleteNotificationChannelGroup()
    }


}