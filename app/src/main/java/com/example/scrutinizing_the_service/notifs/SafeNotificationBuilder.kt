package com.example.scrutinizing_the_service.notifs

import android.content.Context
import android.os.Build

class SafeNotificationBuilder(
    val oreoNotificationBuilder: OreoNotificationBuilder,
    val preOreoNotificationBuilder: PreOreoNotificationBuilder
) : NotificationBuilder {

    override fun createNotification(context: Context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            oreoNotificationBuilder.createNotification(context)
        } else {
            preOreoNotificationBuilder.createNotification(context)
        }
    }

    override fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            oreoNotificationBuilder.createChannel(context)
        } else  {
            preOreoNotificationBuilder.createChannel(context)
        }
    }

}