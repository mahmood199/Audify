package com.example.scrutinizing_the_service.services

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.scrutinizing_the_service.BundleIdentifier
import com.example.scrutinizing_the_service.notifs.ExoPlayerNotificationBuilder
import com.example.scrutinizing_the_service.platform.MusicLocatorV2

@RequiresApi(Build.VERSION_CODES.O)
class ExoPlayerAudioService : Service() {

    companion object {
        const val TAG = "ExoPlayerAudioService"
    }

    private var isPlaying = false
    private val player by lazy {
        ExoPlayer.Builder(this).build()
    }

    private val mediaItems by lazy {
        MusicLocatorV2.fetchAllAudioFilesFromDevice(this).map {
            MediaItem.Builder().apply {
                setUri(it.path)
            }.build()
        }
    }

    private val exoPlayerNotificationBuilder by lazy {
        ExoPlayerNotificationBuilder(this, player)
    }

    override fun onCreate() {
        super.onCreate()
        addItemsToMediaPlayer()
        startForeground(
            ExoPlayerNotificationBuilder.NOTIFICATION_ID,
            exoPlayerNotificationBuilder.getNotification(mediaItems[0])
        )
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
        exoPlayerNotificationBuilder.createUpdatedNotification(mediaItems[0])
        player.seekToDefaultPosition(position)
        player.prepare()
        player.play()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        player.release()
        exoPlayerNotificationBuilder.closeAllNotification()
        super.onDestroy()
    }
}