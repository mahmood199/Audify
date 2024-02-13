package com.example.audify.v2.ui.reminder_settings

data class ReminderSettingsViewState(
    val isLoading: Boolean
) {

    companion object {
        fun default() = ReminderSettingsViewState(
            isLoading = false
        )
    }

}