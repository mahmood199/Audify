package com.download.service.notifs

import android.content.Context
import android.os.Build
import com.download.service.notifs.NotificationBuilder
import com.download.service.notifs.PreOreoNotificationBuilder

class SafeNotificationBuilder(
    val oreoNotificationBuilder: NotificationBuilder,
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

    override fun redirectToSettings() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            oreoNotificationBuilder.redirectToSettings()
        } else {
            preOreoNotificationBuilder.redirectToSettings()
        }
    }

}