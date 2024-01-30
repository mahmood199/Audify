package com.example.audify.v2.ui.catalog

import com.example.data.models.Song

data class MusicListViewState(
    var progress: Float = 0f,
    var loading: Boolean = false,
    var duration: Long = 1L,
    var progressString: String = "00:00",
    var currentSong: Song? = null,
    var isPlaying: Boolean = false,
    var isConnected: Boolean = false,
)