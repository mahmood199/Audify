package com.example.scrutinizing_the_service.v2.ui.player


sealed interface PlayerUiEvent {
    data object PlayPause : PlayerUiEvent
    data object Rewind : PlayerUiEvent
    data object FastForward : PlayerUiEvent
    data object PlayPreviousItem : PlayerUiEvent
    data object PlayNextItem : PlayerUiEvent
    data class PlaySongAt(val index: Int) : PlayerUiEvent
    data class UpdateProgress(val newProgress: Float) : PlayerUiEvent
}