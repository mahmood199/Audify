package com.example.scrutinizing_the_service.notifs.exo_player

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.MediaMetadata
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.media.session.PlaybackState.*
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerNotificationManager
import com.example.scrutinizing_the_service.R

@RequiresApi(Build.VERSION_CODES.O)
class ExoPlayerNotificationBuilder(
    val context: Context,
    private val exoPlayer: ExoPlayer
) {

    companion object {
        const val EXO_PLAYER_CHANNEL_ID = "EXO_PLAYER_CHANNEL_ID"
        const val EXO_PLAYER_CHANNEL_NAME = "EXO_PLAYER_CHANNEL_NAME"
        const val EXO_PLAYER_CHANNEL_DESCRIPTION = "Show exo-player notification for music."

        const val REQUEST_CODE = 100
        const val NOTIFICATION_ID = 12345
        const val TAG = "ExoPlayerNotificationBuilder"
    }

    private val notificationManagerCompat by lazy {
        NotificationManagerCompat.from(context)
    }

    private val actualNotificationManager by lazy {
        context.getSystemService(NotificationManager::class.java)
    }



    private fun getMediaStyle(song: MediaItem): Notification.MediaStyle {
        val mediaSession = getMediaSession(song)
        return Notification.MediaStyle()
            .setMediaSession(mediaSession.sessionToken).setShowActionsInCompactView(0, 1, 2)
    }

    private fun getMediaSession(song: MediaItem): MediaSession {
        return MediaSession(context, TAG).apply {
            setMetadata(
                MediaMetadata
                    .Builder()
                    .putString(
                        MediaMetadata.METADATA_KEY_TITLE,
                        song.mediaMetadata.title.toString()
                    )
                    .putString(
                        MediaMetadata.METADATA_KEY_ARTIST,
                        song.mediaMetadata.albumArtist.toString()
                    )
                    .build()
            )
            setPlaybackState(
                PlaybackState.Builder()
                    .setState(
                        STATE_PLAYING,
                        exoPlayer.currentPosition.toLong(),
                        1.0F
                    )
                    .setActions(
                        ACTION_PLAY or
                                ACTION_PAUSE or
                                ACTION_SKIP_TO_NEXT or
                                ACTION_SKIP_TO_PREVIOUS or
                                ACTION_SEEK_TO
                    ).build()
            )
            /**There is an internal Android MediaSessions limit SESSION_CREATION_LIMIT_PER_UID = 10
             * You should release MediaSession instances that you don't need anymore.
             * else it'll throw the following exception
             * [Attempt to invoke interface method 'android.media.session.ISessionController]
             * [android.media.session.ISession.getController()' on a null object reference]
             */
            release()
        }
    }

    fun createChannel() {
        if (!notificationManagerCompat.areNotificationsEnabled())
            redirectToSettings()

        if (!isChannelBlocked(EXO_PLAYER_CHANNEL_ID))
            redirectToNotificationChannelSettings(EXO_PLAYER_CHANNEL_ID)

        val channel = NotificationChannel(
            EXO_PLAYER_CHANNEL_ID,
            EXO_PLAYER_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        channel.description = EXO_PLAYER_CHANNEL_DESCRIPTION
        notificationManagerCompat.createNotificationChannel(channel)
    }

    fun redirectToSettings() {
        redirectToNotificationChannelSettings(EXO_PLAYER_CHANNEL_ID)
    }

    private fun isChannelBlocked(channelId: String): Boolean {
        val channel = actualNotificationManager.getNotificationChannel(channelId)
        return channel != null && channel.importance != NotificationManager.IMPORTANCE_NONE
    }

    private fun redirectToNotificationChannelSettings(channelId: String) {
        Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS).apply {
            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            putExtra(Settings.EXTRA_CHANNEL_ID, channelId)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }
    }

    fun createUpdatedNotification(song: MediaItem) {
        createChannel()

        val notification = Notification.Builder(context, EXO_PLAYER_CHANNEL_ID)
            .setStyle(getMediaStyle(song))
            .setSmallIcon(R.drawable.placeholder)

        notificationManagerCompat.notify(NOTIFICATION_ID, notification.build())
    }

    fun closeAllNotification() {
        actualNotificationManager.cancelAll()
        notificationManagerCompat.cancelAll()
    }

    fun getNotification(song: MediaItem): Notification? {
        if (!notificationManagerCompat.areNotificationsEnabled())
            redirectToSettings()
        if (!isChannelBlocked(EXO_PLAYER_CHANNEL_ID))
            redirectToNotificationChannelSettings(EXO_PLAYER_CHANNEL_ID)

        val notification = Notification.Builder(
            context,
            EXO_PLAYER_CHANNEL_ID
        )
            .setStyle(getMediaStyle(song))
            .setSmallIcon(R.drawable.placeholder)
            .setOngoing(true)

        return notification.build()
    }


}