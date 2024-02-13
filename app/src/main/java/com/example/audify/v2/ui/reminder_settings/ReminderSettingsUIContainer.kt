package com.example.audify.v2.ui.reminder_settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.audify.v2.theme.AudifyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderSettingsUIContainer(
    modifier: Modifier = Modifier,
    viewModel: ReminderSettingsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    ReminderSettingsUI(
        state = state,
        scheduleAlarm = {
            viewModel.scheduleAlarm(it.hour, it.minute, it.is24hour)
        },
        modifier = modifier
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderSettingsUI(
    state: ReminderSettingsViewState,
    scheduleAlarm: (TimePickerState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        val timePickerState = rememberTimePickerState()
        TimePicker(
            state = timePickerState,
        )

        Text(text = "Selected Time: ${timePickerState.hour}:${timePickerState.minute}")

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            Button(
                onClick = {
                    scheduleAlarm(timePickerState)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Save")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ReminderSettingsUIPreview() {
    AudifyTheme {
        ReminderSettingsUI(
            state = ReminderSettingsViewState.default(),
            scheduleAlarm = {},
            modifier = Modifier.background(Color.Green)
        )
    }
}