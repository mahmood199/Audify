package com.example.scrutinizing_the_service.notifs

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.session.MediaSession
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.ui.music.MusicPlayerActivity

@RequiresApi(Build.VERSION_CODES.O)
class MediaPlayerNotificationBuilder(
    val context: Context
) {

    companion object {
        const val MEDIA_CHANNEL_ID = "MEDIA_CHANNEL_ID"
        const val MEDIA_CHANNEL_NAME = "MEDIA_PLAYER"
        const val MEDIA_CHANNEL_DESCRIPTION = "Show media player notification for music."

        const val REQUEST_CODE = 100
        const val NOTIFICATION_ID = 1234
        const val TAG = "MediaPlayerNotificationBuilder"
    }

    private val notificationManagerCompat by lazy {
        NotificationManagerCompat.from(context)
    }

    private val actualNotificationManager by lazy {
        context.getSystemService(NotificationManager::class.java)
    }

    fun createNotification(context: Context, song: Song) {
        createChannel()
        val areNotificationsEnabled = notificationManagerCompat.areNotificationsEnabled()
        if (!areNotificationsEnabled) {
            redirectToSettings()
            return
        }
        if (!isChannelBlocked(MEDIA_CHANNEL_ID)) {
            redirectToNotificationChannelSettings(MEDIA_CHANNEL_ID)
            return
        }

        val intent = Intent(context, MusicPlayerActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(
                context, REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE
            )

        val mediaSession = MediaSession(context, TAG)
        val mediaStyle = Notification.MediaStyle().setMediaSession(mediaSession.sessionToken)


        val notification = Notification.Builder(context, MEDIA_CHANNEL_ID)
            .setSmallIcon(R.drawable.placeholder)
            .setContentTitle(song.name)
            .setColorized(false)
            .setOngoing(true)
            .setContentText("Here comes the pain")
            .setStyle(mediaStyle)

        notification.addAction(
            Notification.Action.Builder(
                R.drawable.ic_skip_previous, context.getString(R.string.previous), null
            ).build()
        )

        notification.addAction(
            Notification.Action.Builder(
                R.drawable.ic_play, context.getString(R.string.play), null
            ).build()
        )

        notification.addAction(
            Notification.Action.Builder(
                R.drawable.ic_skip_next, context.getString(R.string.next), null
            ).build()
        )

        notificationManagerCompat.notify(NOTIFICATION_ID, notification.build())
    }

    private fun createChannel() {
        if (notificationManagerCompat.getNotificationChannel(MEDIA_CHANNEL_ID) != null)
            return

        val channel = NotificationChannel(
            MEDIA_CHANNEL_ID,
            MEDIA_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = MEDIA_CHANNEL_DESCRIPTION
        notificationManagerCompat.createNotificationChannel(channel)
    }

    fun redirectToSettings() {
        redirectToNotificationChannelSettings(MEDIA_CHANNEL_ID)
    }

    private fun isChannelBlocked(channelId: String): Boolean {
        val channel = actualNotificationManager.getNotificationChannel(channelId)
        return channel != null && channel.importance != NotificationManager.IMPORTANCE_NONE
    }

    private fun redirectToNotificationChannelSettings(channelId: String) {
        Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS).apply {
            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            putExtra(Settings.EXTRA_CHANNEL_ID, channelId)
            context.startActivity(this)
        }
    }


}