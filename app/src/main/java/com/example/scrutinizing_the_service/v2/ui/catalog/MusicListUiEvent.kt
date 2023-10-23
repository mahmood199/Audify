package com.example.scrutinizing_the_service.v2.ui.catalog


sealed interface MusicListUiEvent {
    data class UpdateProgress(val newProgress: Float) : MusicListUiEvent
}