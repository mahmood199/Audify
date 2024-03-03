package com.example.audify.v2.ui.player

import androidx.compose.ui.graphics.Color
import androidx.media3.common.MediaItem
import com.example.data.models.Song


data class AudioPlayerViewState(
    val someData: Boolean,
    var progress: Float,
    var duration: String,
    var progressString: String,
    var currentSong: Song?,
    var isPlaying: Boolean,
    var userSelectedPage: Int,
    var backGroundColor: Color,
    var currentMediaItem: MediaItem?,
) {

    companion object {
        fun default(): AudioPlayerViewState {
            return AudioPlayerViewState(
                someData = false,
                progress = 0f,
                duration = "00:00",
                progressString = "00:00",
                currentSong = null,
                isPlaying = false,
                userSelectedPage = 0,
                backGroundColor = Color.DarkGray,
                currentMediaItem = null,
            )
        }
    }
}