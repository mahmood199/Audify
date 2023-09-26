package com.example.scrutinizing_the_service.v2.ui.search_result


sealed class SearchResultUiEvent {
    data object PlayPause : SearchResultUiEvent()
    data object Rewind : SearchResultUiEvent()
    data object FastForward : SearchResultUiEvent()
    data object PlayPreviousItem : SearchResultUiEvent()
    data object PlayNextItem : SearchResultUiEvent()
    data class PlaySongAt(val index: Int) : SearchResultUiEvent()
    data class UpdateProgress(val newProgress: Float) : SearchResultUiEvent()
}