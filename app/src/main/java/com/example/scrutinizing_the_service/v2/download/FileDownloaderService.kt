package com.example.scrutinizing_the_service.v2.download

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.v2.data.local.datasource.FileDownloadDataSource
import com.example.scrutinizing_the_service.v2.data.models.local.RecentlyPlayed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class FileDownloaderService : Service(), CoroutineScope {

    companion object {
        const val DOWNLOAD_CHANNEL_ID = "file_download_channel_id"
        const val DOWNLOAD_CHANNEL_NAME = "Showing Progress for downloading item"
    }

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    @Inject
    lateinit var downloadDataSource: FileDownloadDataSource

    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val binder = LocalBinder()

    private val downloadTasks by lazy {
        mutableMapOf<String, DownloadTask>()
    }

    private val notifications by lazy {
        mutableMapOf<String, NotificationCompat.Builder>()
    }

    inner class LocalBinder : Binder() {
        fun getService(): FileDownloaderService = this@FileDownloaderService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            DOWNLOAD_CHANNEL_ID,
            DOWNLOAD_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )

        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }

    fun startDownload(recentlyPlayed: RecentlyPlayed) {
        launch {
            Log.d("DownloadPurpose", "startDownload")
            Log.d("DownloadPurpose", "$recentlyPlayed")
            val downloadTask = DownloadTask(recentlyPlayed.downloadUrl, recentlyPlayed.name)
            downloadTasks[recentlyPlayed.id] = downloadTask
            downloadTask.start { progress, sizeInBytes ->

                if (progress == 1.0f) {
                    removeNotification(recentlyPlayed = recentlyPlayed, sizeInBytes)
                    downloadTasks.remove(recentlyPlayed.id)
                    cancel()
                }

                updateDb(
                    recentlyPlayed = recentlyPlayed,
                    progress = progress,
                    sizeInBytes = sizeInBytes
                )

                updateNotification(
                    fileName = recentlyPlayed,
                    progress = (progress * 100).toInt()
                )
            }
        }
    }

    private fun removeNotification(recentlyPlayed: RecentlyPlayed, sizeInBytes: Long) {
        // Instead of cancelling the notif, inform the user that download has completed
        showDownloadCompletedNotifcation()
        notificationManager.cancel(recentlyPlayed.hashCode())
        notifications.remove(recentlyPlayed.name)
        launch(Dispatchers.IO) {
            downloadDataSource.updateDownloadState(recentlyPlayed, 1.0, sizeInBytes)
        }
    }

    private fun showDownloadCompletedNotifcation() {

    }

    private suspend fun updateDb(
        recentlyPlayed: RecentlyPlayed,
        progress: Float,
        sizeInBytes: Long
    ) {
        withContext(Dispatchers.IO) {
            downloadDataSource.updateDownloadState(recentlyPlayed, progress.toDouble(), sizeInBytes)
        }
    }

    private suspend fun updateNotification(fileName: RecentlyPlayed, progress: Int) {
        withContext(Dispatchers.Main) {
            delay(100)
            val notificationBuilder = notifications[fileName.id]

            val builder = notificationBuilder?.setProgress(100, progress, false)?.setOngoing(true)
                ?: NotificationCompat.Builder(this@FileDownloaderService, DOWNLOAD_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_file_download_notification)
                    .setProgress(100, progress, false)
                    .setContentTitle("Downloading: ${fileName.name}")
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setOngoing(true)

            notifications[fileName.name] = builder

            notificationManager.notify(fileName.hashCode(), builder.build())
        }
    }

}