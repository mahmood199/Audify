package com.example.audify.v2.ui.reminder_settings

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.audify.reminder.NotificationAlarmScheduler
import com.example.audify.reminder.ReminderItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ReminderSettingsViewModel @Inject constructor(
    private val notificationAlarmScheduler: NotificationAlarmScheduler
) : ViewModel() {

    private val _state = MutableStateFlow(ReminderSettingsViewState.default())
    val state = _state.asStateFlow()


    fun scheduleAlarm(hour: Int, minute: Int, is24hour: Boolean) {

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        logCalendarInfo(calendar)
        notificationAlarmScheduler.schedule(
            reminderItem = ReminderItem(
                time = calendar.timeInMillis,
                id = 1
            )
        )

        calendar.add(Calendar.HOUR_OF_DAY, 12)

        notificationAlarmScheduler.schedule(
            reminderItem = ReminderItem(
                time = calendar.timeInMillis,
                id = 2
            )
        )

        logCalendarInfo(calendar)
    }

    private fun logCalendarInfo(calendar: Calendar) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        Log.d("logCalendarInfo:", "$year/$month/$day----$hour:$minute:$second")
    }


}