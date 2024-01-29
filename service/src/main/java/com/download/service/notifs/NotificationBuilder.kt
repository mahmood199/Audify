package com.download.service.notifs

import android.content.Context

interface NotificationBuilder {

    fun createNotification(context: Context)
    fun createChannel(context: Context)
    fun redirectToSettings()

}