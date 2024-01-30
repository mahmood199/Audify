package com.example.audify.v2.ui.settings

data class SettingsViewState(
    val isLoading: Boolean
) {
    companion object {
        fun default(): SettingsViewState {
            return SettingsViewState(false)
        }
    }
}