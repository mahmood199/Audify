package com.example.scrutinizing_the_service.services.exo_player

import android.app.Notification
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerNotificationManager

@UnstableApi
class MyNotificationListener(private val service: ExoPlayerAudioService) :
    PlayerNotificationManager.NotificationListener {

    override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
    }

    override fun onNotificationPosted(
        notificationId: Int,
        notification: Notification,
        ongoing: Boolean
    ) {
        service.updateNotification(notification)
    }
}
