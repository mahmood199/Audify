package com.example.scrutinizing_the_service.v2.media3

import android.content.Intent
import android.os.Build
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@UnstableApi
@AndroidEntryPoint
class AudioPlayerService : MediaSessionService() {

    @Inject
    lateinit var mediaSession: MediaSession

    @Inject
    lateinit var notificationManager: SimpleMediaNotificationManager

    override fun onGetSession(
        controllerInfo: MediaSession.ControllerInfo
    ) = mediaSession

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.startNotificationService(
                mediaSession = mediaSession,
                mediaSessionService = this
            )
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        clearResources()
        stopSelf()
    }

    private fun clearResources() {
        with(mediaSession) {
            release()
            player.stop()
            player.release()
        }
        if (::notificationManager.isInitialized) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.cancel()
            }
        }
    }

    override fun onDestroy() {
        clearResources()
        super.onDestroy()
    }

    companion object {
        const val TAG = "AudioPlayerService"
    }
}