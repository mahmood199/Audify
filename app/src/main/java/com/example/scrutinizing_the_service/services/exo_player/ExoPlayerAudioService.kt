package com.example.scrutinizing_the_service.services.exo_player

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.annotation.RequiresApi
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerNotificationManager
import com.example.scrutinizing_the_service.BundleIdentifier
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.notifs.exo_player.ExoPlayerNotificationBuilder
import com.example.scrutinizing_the_service.platform.MusicLocatorV2
import com.example.scrutinizing_the_service.ui.MainActivity

@UnstableApi @RequiresApi(Build.VERSION_CODES.O)
class ExoPlayerAudioService : Service() {

    companion object {
        const val TAG = "ExoPlayerAudioService"
    }

    private var isPlaying = false
    private val player by lazy {
        ExoPlayer.Builder(this).build()
    }

    val mediaSessionCompat by lazy {
        MediaSessionCompat(this, TAG)
    }

    private val mediaItems by lazy {
        MusicLocatorV2.fetchAllAudioFilesFromDevice(this).map {
            MediaItem.Builder().apply {
                setUri(it.path)
            }.build()
        }
    }

    val mediaDescriptionAdapter = object : PlayerNotificationManager.MediaDescriptionAdapter {
        override fun getCurrentContentTitle(player: Player): CharSequence {
            // Return the title of the currently playing media item.
            val mediaItem = player.currentMediaItem ?: return ""
            return mediaItem.mediaMetadata.title ?: ""
        }

        override fun getCurrentContentText(player: Player): CharSequence {
            // Return the description of the currently playing media item.
            val mediaItem = player.currentMediaItem ?: return ""
            return mediaItem.mediaMetadata.artist ?: ""
        }

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {
            // Return the large icon for the notification.
            return BitmapFactory.decodeResource(resources, R.drawable.ic_play)
        }

        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            // Return an intent to open the app when the notification is clicked.
            val intent = Intent(this@ExoPlayerAudioService, MainActivity::class.java)
            return PendingIntent.getActivity(this@ExoPlayerAudioService, 0, intent, 0)
        }
    }

    private val playerNotificationManager by lazy {
        PlayerNotificationManager.Builder(
            this,
            ExoPlayerNotificationBuilder.NOTIFICATION_ID,
            ExoPlayerNotificationBuilder.EXO_PLAYER_CHANNEL_ID
        ).setMediaDescriptionAdapter(
            mediaDescriptionAdapter
        ).build()
    }

    val mediaController by lazy {
        MediaControllerCompat(this, mediaSessionCompat.sessionToken)
    }

    override fun onCreate() {
        super.onCreate()
        addItemsToMediaPlayer()
/*
        startForeground(
            ExoPlayerNotificationBuilder.NOTIFICATION_ID,
        )
*/

        playerNotificationManager.setMediaSessionToken(mediaSessionCompat.sessionToken)
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_IDLE || state == Player.STATE_ENDED) {
                    // Remove the notification when playback stops.
                    playerNotificationManager.setPlayer(null)
                } else {
                    // Update the notification when playback starts or resumes.
                    playerNotificationManager.setPlayer(player)
                }
            }
        })
        playerNotificationManager
    }

    private fun addItemsToMediaPlayer() {
        player.addMediaItems(mediaItems)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            val command = it.getStringExtra(BundleIdentifier.BUTTON_ACTION)
            val position = it.getIntExtra(BundleIdentifier.SONG_POSITION, 0)
            play(position)
        }
        return START_STICKY
    }

    private fun play(position: Int) {
        player.seekToDefaultPosition(position)
        player.prepare()
        player.play()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        player.release()
        super.onDestroy()
    }

    fun updateNotification(notification: Notification) {

    }
}