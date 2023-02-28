package com.example.scrutinizing_the_service

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.scrutinizing_the_service.notifs.OreoNotificationBuilder
import com.example.scrutinizing_the_service.notifs.PreOreoNotificationBuilder
import com.example.scrutinizing_the_service.notifs.SafeNotificationBuilder

@RequiresApi(Build.VERSION_CODES.O)
class ServiceApplication : Application() {

    companion object {
        const val CHANNEL_1_ID = "CHANNEL_1_ID"
        const val CHANNEL_2_ID = "CHANNEL_2_ID"
    }

    val safeNotificationBuilder by lazy {
        SafeNotificationBuilder(
            OreoNotificationBuilder(this),
            PreOreoNotificationBuilder(this)
        )
    }

    override fun onCreate() {
        super.onCreate()

        safeNotificationBuilder.createChannel(this)
        safeNotificationBuilder.createNotification(this)
    }
}