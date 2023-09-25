
package com.example.scrutinizing_the_service.v2.reminder

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.media3.common.util.UnstableApi
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.v2.AudioPlayerActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@UnstableApi
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var reminderManager: ReminderManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendReminderNotification(
            applicationContext = context,
            channelId = "Gentle reminder"
        )
        // Remove this line if you don't want to reschedule the reminder
        reminderManager.startReminder()
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@UnstableApi
fun NotificationManager.sendReminderNotification(
    applicationContext: Context,
    channelId: String,
) {
    val contentIntent = Intent(applicationContext, AudioPlayerActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        applicationContext,
        1,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    val builder = NotificationCompat.Builder(applicationContext, channelId)
        .setContentTitle("Long Time No Sea...")
        .setContentText("Listen to your favourite music right away")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("Hello there")
        )
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}

const val NOTIFICATION_ID = 181