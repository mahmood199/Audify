package com.download.service

data class MediaPlayerStatus(
    val currentPlayingTime: Long,
    val totalDuration: Long,
    val isPlaying: Boolean
)