package com.example.audify.v2.ui.reminder_settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.audify.v2.theme.AudifyTheme

@Composable
fun ReminderSettingsUIContainer(
    modifier: Modifier = Modifier,
    viewModel: ReminderSettingsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    ReminderSettingsUI(
        state = state,
        modifier = modifier
    )

}

@Composable
fun ReminderSettingsUI(
    state: ReminderSettingsViewState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {

    }
}

@Preview
@Composable
private fun ReminderSettingsUIPreview() {
    AudifyTheme {
        ReminderSettingsUI(ReminderSettingsViewState.default())
    }
}