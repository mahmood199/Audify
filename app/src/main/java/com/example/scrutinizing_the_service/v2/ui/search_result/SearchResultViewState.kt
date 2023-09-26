package com.example.scrutinizing_the_service.v2.ui.search_result

import com.example.scrutinizing_the_service.data.Song

data class SearchResultViewState(
    val someData: Boolean = false,
    var progress: Float = 0f,
    var duration: Long = 1L,
    var progressString: String = "00:00",
    var currentSong: Song? = null,
    var isPlaying: Boolean = false
)