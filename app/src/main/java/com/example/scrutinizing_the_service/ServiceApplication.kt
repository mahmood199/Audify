package com.example.scrutinizing_the_service

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.media3.common.util.UnstableApi
import com.example.scrutinizing_the_service.v2.reminder.ReminderManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.EmptyCoroutineContext

@UnstableApi
@HiltAndroidApp
class ServiceApplication : Application() {


    private val coroutineScope by lazy {
        CoroutineScope(EmptyCoroutineContext)
    }

    @Inject
    lateinit var reminderManager: ReminderManager


    override fun onCreate() {
        super.onCreate()
        initializeChannelsAsync()
    }

    private fun initializeChannelsAsync() {
        coroutineScope.launch(context = Dispatchers.IO) {
            createNotificationsChannels()
            reminderManager.startReminder()
        }
    }

    private fun createNotificationsChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                ReminderManager.CHANNEL_ID,
                ReminderManager.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            ContextCompat.getSystemService(this, NotificationManager::class.java)
                ?.createNotificationChannel(channel)
        }
    }

    fun shutDownResources() {
        coroutineScope.cancel()
    }


}