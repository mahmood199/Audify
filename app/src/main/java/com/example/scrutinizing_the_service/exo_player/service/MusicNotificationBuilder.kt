package com.example.scrutinizing_the_service.exo_player.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.media.session.MediaSession
import android.os.Build
import android.support.v4.media.session.PlaybackStateCompat
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.media.session.MediaButtonReceiver
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.notifs.MediaPlayerNotificationBuilder

@RequiresApi(Build.VERSION_CODES.O)
class MusicNotificationBuilder(
    private val service: Service,
    private val mediaSession: MediaSession
) {

    private val notificationManagerCompat by lazy {
        NotificationManagerCompat.from(service)
    }


    companion object {
        const val PLAYER_NOTIFICATION_CHANNEL_ID = "PLAYER_NOTIFICATION_CHANNEL_ID"
        const val PLAYER_NOTIFICATION_CHANNEL_NAME = "PLAYER_NOTIFICATION_CHANNEL_NAME"
        const val PLAYER_NOTIFICATION_ID = 2000
    }

    val controller = mediaSession.controller
    val mediaMetaData = controller.metadata
    val description = mediaMetaData?.description


    val builder = Notification.Builder(service, PLAYER_NOTIFICATION_CHANNEL_ID).apply {
        setContentTitle(description?.title ?: "")
        setContentText(description?.subtitle ?: "")
        setSubText(description?.description ?: "")
        description?.iconBitmap?.let {
            setLargeIcon(it)
        }

        setContentIntent(controller.sessionActivity)

        setDeleteIntent(
            MediaButtonReceiver.buildMediaButtonPendingIntent(
                service,
                PlaybackStateCompat.ACTION_STOP
            )
        )

        setVisibility(Notification.VISIBILITY_PUBLIC)

        setSmallIcon(R.drawable.ic_launcher_foreground)

        setColor(ContextCompat.getColor(service, R.color.purple_700))

        addAction(
            Notification.Action(
                R.drawable.ic_pause,
                service.getString(R.string.pause),
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    service,
                    PlaybackStateCompat.ACTION_PAUSE
                )
            )
        )

        mediaSession.let {
            style = Notification.MediaStyle()
                .setMediaSession(it.sessionToken)
                .setShowActionsInCompactView(0)
        }


    }

    fun showNotification() {
        createChannel()
        builder.build()
        service.startForeground(PLAYER_NOTIFICATION_ID, builder.build())
    }

    fun createChannel() {
        if (notificationManagerCompat.getNotificationChannel(MediaPlayerNotificationBuilder.MEDIA_CHANNEL_ID) != null)
            return

        val channel = NotificationChannel(
            PLAYER_NOTIFICATION_CHANNEL_ID,
            PLAYER_NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        channel.description = MediaPlayerNotificationBuilder.MEDIA_CHANNEL_DESCRIPTION
        notificationManagerCompat.createNotificationChannel(channel)
    }


}