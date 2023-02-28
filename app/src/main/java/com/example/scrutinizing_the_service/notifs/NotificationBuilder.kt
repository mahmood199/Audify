package com.example.scrutinizing_the_service.notifs

import android.content.Context

interface NotificationBuilder {

    fun createNotification(context: Context)
    fun createChannel(context: Context)

}