package com.example.scrutinizing_the_service.notifs

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.scrutinizing_the_service.broadcastReceivers.MediaBroadcastReceiver

@RequiresApi(Build.VERSION_CODES.O)
class PendingIntentHelper(
    val context: Context
) {

    val intent = Intent(context, MediaBroadcastReceiver::class.java)

    private val flag = PendingIntent.FLAG_IMMUTABLE

    fun getActionBasedPendingIntent(string: String): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            0,
            intent.apply {
                action = string
            },
            flag
        )
    }


}