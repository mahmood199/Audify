package com.example.scrutinizing_the_service.v2.media3

import androidx.media3.common.MediaItem

sealed interface PlayerEvent {
    data object PlayPause : PlayerEvent
    data object Rewind : PlayerEvent
    data object FastForward : PlayerEvent
    data class PlaySongAt(val index: Int) : PlayerEvent
    data object PlayPreviousItem : PlayerEvent
    data object PlayNextItem : PlayerEvent
    data class UpdateProgress(val newProgress: Float) : PlayerEvent
}

sealed interface PlayerState {
    data object Initial : PlayerState
    data class Ready(val duration: Long) : PlayerState
    data class Progress(val progress: Long, val duration: Long) : PlayerState
    data class Buffering(val progress: Long, val duration: Long) : PlayerState
    data class Playing(val isPlaying: Boolean) : PlayerState
    data class CurrentPlaying(val mediaItem: MediaItem?, val duration: Long) : PlayerState
}
