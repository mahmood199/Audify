package com.example.scrutinizing_the_service.v2.ui.player

import androidx.compose.ui.graphics.Color
import androidx.media3.common.MediaItem
import com.example.data.models.Song


data class AudioPlayerViewState(
    val someData: Boolean = false,
    var progress: Float = 0f,
    var duration: String = "00:00",
    var progressString: String = "00:00",
    var currentSong: Song? = null,
    var isPlaying: Boolean = false,
    var userSelectedPage: Int = 0,
    var backGroundColor: Color = Color.DarkGray,
    var currentMediaItem: MediaItem? = null
) {
}