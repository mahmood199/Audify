package com.example.scrutinizing_the_service.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.scrutinizing_the_service.platform.MusicLocatorV2
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.upstream.DefaultDataSource

class MusicPlayerServiceV2 : Service() {

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

    override fun onCreate() {
        super.onCreate()

        addItemsToMediaPlayer()
        play()

    }

    private fun play() {
        player.prepare()
        player.play()
    }

    private fun addItemsToMediaPlayer() {
        player.addMediaItems(mediaItems)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        player.release()
        super.onDestroy()
    }
}