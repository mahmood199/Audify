package com.example.audify.v2.ui.reminder_settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ReminderSettingsViewModel @Inject constructor(

) : ViewModel() {


    private val _state = MutableStateFlow(ReminderSettingsViewState.default())
    val state = _state.asStateFlow()


}