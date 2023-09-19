package com.example.scrutinizing_the_service.v2.ui.catalog


sealed interface MusicListUiEvent {
    data object PlayPause : MusicListUiEvent
    data object Rewind : MusicListUiEvent
    data object FastForward : MusicListUiEvent
    data object PlayPreviousItem : MusicListUiEvent
    data object PlayNextItem : MusicListUiEvent
    data class PlaySongAt(val index: Int) : MusicListUiEvent
    data class UpdateProgress(val newProgress: Float) : MusicListUiEvent
}