package com.download.service.notifs

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.MediaMetadata
import android.media.MediaPlayer
import android.media.session.MediaSession
import android.media.session.PlaybackState.ACTION_PAUSE
import android.media.session.PlaybackState.ACTION_PLAY
import android.media.session.PlaybackState.ACTION_SEEK_TO
import android.media.session.PlaybackState.ACTION_SKIP_TO_NEXT
import android.media.session.PlaybackState.ACTION_SKIP_TO_PREVIOUS
import android.media.session.PlaybackState.Builder
import android.media.session.PlaybackState.STATE_PLAYING
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.download.service.MediaActionEmitter
import com.download.service.R
import com.example.data.models.Song

@RequiresApi(Build.VERSION_CODES.O)
class MediaPlayerNotificationBuilder(
    val context: Context,
    val mediaPlayer: MediaPlayer
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

    private val pendingIntentHelper by lazy {
        PendingIntentHelper(context)
    }

    fun getNotification(song: Song, currentPlayingTime: Int): Notification {
        if (!notificationManagerCompat.areNotificationsEnabled())
            redirectToSettings()
        if (!isChannelBlocked(MEDIA_CHANNEL_ID))
            redirectToNotificationChannelSettings(MEDIA_CHANNEL_ID)

        val notification = Notification.Builder(context, MEDIA_CHANNEL_ID)
            .setStyle(getMediaStyle(song, currentPlayingTime))
            .setSmallIcon(R.drawable.placeholder)
            .setOngoing(true)
            .addAction(
                Notification.Action.Builder(
                    R.drawable.ic_skip_previous,
                    context.getString(R.string.previous),
                    pendingIntentHelper.getActionBasedPendingIntent(MediaActionEmitter.PREVIOUS)
                ).build()
            ).addAction(
                Notification.Action.Builder(
                    if (mediaPlayer.isPlaying)
                        R.drawable.ic_pause
                    else
                        R.drawable.ic_play,
                    if (mediaPlayer.isPlaying)
                        context.getString(R.string.pause)
                    else
                        context.getString(R.string.play),
                    if (mediaPlayer.isPlaying)
                        pendingIntentHelper.getActionBasedPendingIntent(MediaActionEmitter.PAUSE)
                    else
                        pendingIntentHelper.getActionBasedPendingIntent(MediaActionEmitter.PLAY)
                ).build()
            )
            .addAction(
                Notification.Action.Builder(
                    R.drawable.ic_skip_next,
                    context.getString(R.string.next),
                    pendingIntentHelper.getActionBasedPendingIntent(MediaActionEmitter.NEXT)
                ).build()
            )

        return notification.build()
    }

    private fun getMediaStyle(song: Song, currentPosition: Int): Notification.MediaStyle {
        val mediaSession = getMediaSession(song, currentPosition)
        return Notification.MediaStyle()
            .setMediaSession(mediaSession.sessionToken).setShowActionsInCompactView(0, 1, 2)
    }

    private fun getMediaSession(song: Song, currentPosition: Int): MediaSession {
        return MediaSession(context, TAG).apply {
            setMetadata(
                MediaMetadata
                    .Builder()
                    .putString(MediaMetadata.METADATA_KEY_TITLE, song.name)
                    .putString(MediaMetadata.METADATA_KEY_ARTIST, song.artist)
                    .putLong(MediaMetadata.METADATA_KEY_DURATION, currentPosition.toLong())
                    .build()
            )
            setPlaybackState(
                Builder()
                    .setState(
                        STATE_PLAYING,
                        currentPosition.toLong(),
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
        if (notificationManagerCompat.getNotificationChannel(MEDIA_CHANNEL_ID) != null)
            return

        val channel = NotificationChannel(
            MEDIA_CHANNEL_ID,
            MEDIA_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
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
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }
    }

    @SuppressLint("MissingPermission")
    fun createUpdatedNotification(song: Song, currentPosition: Int, playing: Boolean) {

        val notification = Notification.Builder(context, MEDIA_CHANNEL_ID)
            .setStyle(getMediaStyle(song, currentPosition))
            .setSmallIcon(R.drawable.placeholder)
            .setColorized(playing)
            .setOngoing(playing)

        notification.addAction(
            Notification.Action.Builder(
                R.drawable.ic_skip_previous,
                context.getString(R.string.previous),
                pendingIntentHelper.getActionBasedPendingIntent(MediaActionEmitter.PREVIOUS)
            ).build()
        )

        notification.addAction(
            Notification.Action.Builder(
                if (mediaPlayer.isPlaying)
                    R.drawable.ic_pause
                else
                    R.drawable.ic_play,
                if (mediaPlayer.isPlaying)
                    context.getString(R.string.pause)
                else
                    context.getString(R.string.play),
                if (mediaPlayer.isPlaying)
                    pendingIntentHelper.getActionBasedPendingIntent(MediaActionEmitter.PAUSE)
                else
                    pendingIntentHelper.getActionBasedPendingIntent(MediaActionEmitter.PLAY)
            ).build()
        )

        notification.addAction(
            Notification.Action.Builder(
                R.drawable.ic_skip_next,
                context.getString(R.string.next),
                pendingIntentHelper.getActionBasedPendingIntent(MediaActionEmitter.NEXT)
            ).build()
        )

        notificationManagerCompat.notify(NOTIFICATION_ID, notification.build())
    }

    fun closeAllNotification() {
        actualNotificationManager.cancelAll()
        notificationManagerCompat.cancelAll()
    }


}