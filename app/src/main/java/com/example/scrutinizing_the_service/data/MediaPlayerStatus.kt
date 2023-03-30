package com.example.scrutinizing_the_service.data

data class MediaPlayerStatus(
    val currentPlayingTime: Long,
    val totalDuration: Long,
    val song: Song,
    val isPlaying: Boolean
)