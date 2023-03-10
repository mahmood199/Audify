package com.example.scrutinizing_the_service.notifs

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaMetadata
import android.media.MediaPlayer
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.media.session.PlaybackState.*
import android.os.Build
import android.provider.Settings
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.TimeConverter
import com.example.scrutinizing_the_service.broadcastReceivers.MediaActionEmitter
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.ui.music.MusicPlayerActivity

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

    private var notification = Notification()

    fun createNotification(context: Context, song: Song): Notification? {
        createChannel()
        val areNotificationsEnabled = notificationManagerCompat.areNotificationsEnabled()
        if (!areNotificationsEnabled) {
            redirectToSettings()
            return null
        }
        if (!isChannelBlocked(MEDIA_CHANNEL_ID)) {
            redirectToNotificationChannelSettings(MEDIA_CHANNEL_ID)
            return null
        }

        val intent = Intent(context, MusicPlayerActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(
                context, REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE
            )

        val notificationBuilder = Notification.Builder(context, MEDIA_CHANNEL_ID)
            .setStyle(getMediaStyle(song, 0))
            .setSmallIcon(R.drawable.placeholder)
            .setColorized(true)
            .setOngoing(true)

        notificationBuilder.addAction(
            Notification.Action.Builder(
                R.drawable.ic_skip_previous, context.getString(R.string.previous), null
            ).build()
        )

        notificationBuilder.addAction(
            Notification.Action.Builder(
                R.drawable.ic_play, context.getString(R.string.play),
                PendingIntent.getActivity(
                    context,
                    REQUEST_CODE,
                    Intent(
                        if (mediaPlayer.isPlaying)
                            MediaActionEmitter.PAUSE
                        else
                            MediaActionEmitter.PLAY
                    ),
                    PendingIntent.FLAG_IMMUTABLE
                )
            ).build()
        )

        notificationBuilder.addAction(
            Notification.Action.Builder(
                R.drawable.ic_skip_next, context.getString(R.string.next), null
            ).build()
        )

        notificationManagerCompat.notify(NOTIFICATION_ID, notificationBuilder.build())
        return notificationBuilder.build()
    }

    fun getNotification(song: Song, currentPlayingTime: Int): Notification {
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
                    if(mediaPlayer.isPlaying)
                        R.drawable.ic_pause
                    else
                        R.drawable.ic_play,
                    if(mediaPlayer.isPlaying)
                        context.getString(R.string.pause)
                    else
                        context.getString(R.string.play),
                    if(mediaPlayer.isPlaying)
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
            .setMediaSession(mediaSession.sessionToken).setShowActionsInCompactView(0,1,2)
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
                PlaybackState.Builder()
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
            context.startActivity(this)
        }
    }

    fun createUpdatedNotification(song: Song, currentPosition: Int) {

        val notification = Notification.Builder(context, MEDIA_CHANNEL_ID)
            .setStyle(getMediaStyle(song, currentPosition))
            .setSmallIcon(R.drawable.placeholder)
            .setColorized(true)
            .setOngoing(true)

        notification.addAction(
            Notification.Action.Builder(
                R.drawable.ic_skip_previous,
                context.getString(R.string.previous),
                pendingIntentHelper.getActionBasedPendingIntent(MediaActionEmitter.PREVIOUS)
            ).build()
        )

        notification.addAction(
            Notification.Action.Builder(
                if(mediaPlayer.isPlaying)
                    R.drawable.ic_pause
                else
                    R.drawable.ic_play,
                if(mediaPlayer.isPlaying)
                    context.getString(R.string.pause)
                else
                    context.getString(R.string.play),
                if(mediaPlayer.isPlaying)
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

    @SuppressLint("RemoteViewLayout")
    fun createCustomLayoutNotification(song: Song, currentPosition: Int) {
        val remoteViews =
            RemoteViews(context.packageName, R.layout.layout_media_player_notification).apply {
                setTextViewText(R.id.tv_song_name, song.name)
                setTextViewText(R.id.tv_song_artist, song.artist)
                setTextViewText(
                    R.id.tv_current_time,
                    TimeConverter.getConvertedTime(currentPosition.toLong())
                )
                setTextViewText(
                    R.id.tv_total_time_notif,
                    TimeConverter.getConvertedTime(song.duration.toLong())
                )
            }
        val notification = Notification.Builder(context, MEDIA_CHANNEL_ID)
            .setSmallIcon(R.drawable.placeholder)
            .setColorized(true)
            .setOngoing(true)
            .setCustomContentView(remoteViews)
            .setCustomBigContentView(remoteViews)
            .setCustomHeadsUpContentView(remoteViews)
            .build()
        notificationManagerCompat.notify(NOTIFICATION_ID, notification)
    }


}