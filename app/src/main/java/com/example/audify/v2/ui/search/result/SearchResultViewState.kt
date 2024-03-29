package com.example.audify.v2.ui.search.result

import com.example.data.models.Song

data class SearchResultViewState(
    val someData: Boolean = false,
    var progress: Float = 0f,
    var duration: Long = 1L,
    var progressString: String = "00:00",
    var currentSong: Song? = null,
    var isPlaying: Boolean = false,
    var userSelectedPage: Int = 0
)