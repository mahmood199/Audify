package com.example.scrutinizing_the_service.v2.ui.player


sealed interface PlayerUiEvent {
    data class UpdateProgress(val newProgress: Float) : PlayerUiEvent
}