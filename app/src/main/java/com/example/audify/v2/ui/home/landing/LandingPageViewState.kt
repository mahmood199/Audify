package com.example.audify.v2.ui.home.landing

import com.example.data.models.Song

data class LandingPageViewState(
    var userSelectedPage: Int = 0,
    val isLoading: Boolean = false,
    var progress: Float = 0f,
    val isConnected: Boolean = false,
    var duration: Long = 1L,
    val isPlaying: Boolean = false,
    val currentSong: Song? = null
)