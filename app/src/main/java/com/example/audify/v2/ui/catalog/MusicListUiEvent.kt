package com.example.audify.v2.ui.catalog


sealed interface MusicListUiEvent {
    data class UpdateProgress(val newProgress: Float) : MusicListUiEvent
}