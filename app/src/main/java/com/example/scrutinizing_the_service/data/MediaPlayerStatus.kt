package com.example.scrutinizing_the_service.data

import com.example.data.models.Song

data class MediaPlayerStatus(
    val currentPlayingTime: Long,
    val totalDuration: Long,
    val song: Song,
    val isPlaying: Boolean
)