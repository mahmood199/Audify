package com.example.scrutinizing_the_service.v2.ui.notif

import androidx.compose.runtime.mutableStateListOf

data class NotificationTestViewState(
    val isLoading: Boolean,
    var notifications: MutableList<Pair<Int, NotificationModel>>
) {
    companion object {
        fun default(): NotificationTestViewState {
            return NotificationTestViewState(
                isLoading = false,
                notifications = mutableStateListOf()
            )
        }
    }


}