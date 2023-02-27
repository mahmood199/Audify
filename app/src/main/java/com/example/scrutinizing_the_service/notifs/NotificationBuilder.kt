package com.example.scrutinizing_the_service.notifs

import android.app.Service

interface NotificationBuilder {

    fun createNotification(context: Service)

}