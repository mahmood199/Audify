package com.example.audify.v2.media3

import androidx.media3.common.MediaItem
import androidx.media3.common.Player

interface PlayerDelegate : Player.Listener {

    fun addItems(mediaItems: List<MediaItem>)

    suspend fun onPlayerEvents(playerEvent: PlayerEvent)

    suspend fun playOrPause()

    suspend fun startProgressUpdate()

    fun stopProgressUpdate()

    fun nestedOnIsPlayingChanged(isPlaying: Boolean)

    fun nestedOnPlaybackStateChanged(playbackState: Int)

}