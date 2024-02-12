package com.example.audify.reminder

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.run {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val runnerNotifier = RunnerNotifier(notificationManager, this)
            runnerNotifier.showNotification()
        }
    }

}