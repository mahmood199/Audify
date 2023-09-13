package com.example.scrutinizing_the_service.v2.media3

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.ui.PlayerNotificationManager
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.data.Song

private const val NOTIFICATION_ID = 200
private const val NOTIFICATION_CHANNEL_NAME = "notification channel 1"
private const val NOTIFICATION_CHANNEL_ID = "notification channel id 1"


@UnstableApi
@RequiresApi(Build.VERSION_CODES.O)
class SimpleMediaNotificationManager(
    private val context: Context,
    private val player: Player,
    private val mediaSession: MediaSession
) {

    private var notificationManager = NotificationManagerCompat.from(context)

    init {
        createNotificationChannel()
    }

    @UnstableApi
    fun startNotificationService(
        mediaSessionService: MediaSessionService,
        mediaSession: MediaSession,
        song: Song
    ) {
        buildNotification(mediaSession, song)
        startForegroundNotification(mediaSessionService)
    }

    @UnstableApi
    private fun buildNotification(mediaSession: MediaSession, song: Song) {
        val playerNotificationManager = PlayerNotificationManager
            .Builder(context, NOTIFICATION_ID, NOTIFICATION_CHANNEL_ID)
            .setMediaDescriptionAdapter(
                SimpleMediaNotificationAdapter(
                    context = context,
                    pendingIntent = mediaSession.sessionActivity,
                    song = song
                )
            )
            .setSmallIconResourceId(R.drawable.ic_launcher_foreground)
            .build()

        with(playerNotificationManager) {
            setMediaSessionToken(mediaSession.sessionCompatToken)

            setUseFastForwardActionInCompactView(true)
            setUsePlayPauseActions(true)
            setUseRewindActionInCompactView(true)
            setUseNextActionInCompactView(true)
            setPriority(NotificationCompat.PRIORITY_LOW)

            setPlayer(player)
        }
    }

    private fun startForegroundNotification(mediaSessionService: MediaSessionService) {
        val notification = Notification.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        mediaSessionService.startForeground(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

    fun cancel() {
        notificationManager.cancelAll()
        notificationManager.cancel(NOTIFICATION_ID)
    }
}