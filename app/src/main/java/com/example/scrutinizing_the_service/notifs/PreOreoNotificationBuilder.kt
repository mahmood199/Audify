package com.example.scrutinizing_the_service.notifs

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

class PreOreoNotificationBuilder(
    val context: Context
) : NotificationBuilder {

    override fun createNotification(context: Context) {

    }

    override fun createChannel(context: Context) {

    }

    override fun redirectToSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.parse("package:${context.packageName}")
            context.startActivity(this)
        }
    }

}