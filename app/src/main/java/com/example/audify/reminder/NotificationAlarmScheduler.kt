package com.example.audify.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationAlarmScheduler @Inject constructor(
    @ApplicationContext val context: Context
) : AlarmScheduler {

    private val alarmManager by lazy {
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    override fun createPendingIntent(reminderItem: ReminderItem): PendingIntent {
        val intent = Intent()

        return PendingIntent.getBroadcast(
            context,
            reminderItem.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun schedule(reminderItem: ReminderItem) {
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            reminderItem.time,
            AlarmManager.INTERVAL_DAY,
            createPendingIntent(reminderItem)
        )
    }

    override fun cancel(reminderItem: ReminderItem) {
        alarmManager.cancel(
            createPendingIntent(reminderItem)
        )
    }
}