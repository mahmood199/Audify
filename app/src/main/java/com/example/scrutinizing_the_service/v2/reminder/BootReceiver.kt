package com.example.scrutinizing_the_service.v2.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.media3.common.util.UnstableApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@UnstableApi
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var reminderManager: ReminderManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            reminderManager.startReminder()
        }
    }

}