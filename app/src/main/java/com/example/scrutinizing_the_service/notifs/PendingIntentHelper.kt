package com.example.scrutinizing_the_service.notifs

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.scrutinizing_the_service.broadcastReceivers.MediaBroadcastReceiver

class PendingIntentHelper(
    val context: Context
) {

    val intent = Intent(context, MediaBroadcastReceiver::class.java)

    private val flag =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            PendingIntent.FLAG_IMMUTABLE
        else
            0

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