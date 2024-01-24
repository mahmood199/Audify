package com.example.scrutinizing_the_service.v2.ui.app_icon_change

data class IconChangeViewState(
    val isLoading: Boolean
) {
    companion object {
        fun default(): IconChangeViewState {
            return IconChangeViewState(false)
        }
    }

}