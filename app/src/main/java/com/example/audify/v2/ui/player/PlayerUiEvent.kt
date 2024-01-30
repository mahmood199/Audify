package com.example.audify.v2.ui.player


sealed interface PlayerUiEvent {
    data class UpdateProgress(val newProgress: Float) : PlayerUiEvent
}