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

                if (progress == 1f) {
                    Log.d("DownloadPurpose", "DownloadCompleted")
                    removeNotification(recentlyPlayed)
                    cancel()
                }
                Log.d("DownloadPurpose", "Progress->${progress * 100}")
                withContext(Dispatchers.IO) {
                    updateDb(recentlyPlayed.name, progress * 100, sizeInBytes)
                }

                withContext(Dispatchers.Main) {
                    updateNotification(recentlyPlayed, (progress * 100).toInt())
                }
            }
        }
    }

    private fun removeNotification(recentlyPlayed: RecentlyPlayed) {
        notificationManager.cancel(recentlyPlayed.hashCode())
    }

    private suspend fun updateDb(fileName: String, progress: Float, sizeInBytes: Long) {
        withContext(Dispatchers.IO) {
            downloadDataSource.updateDownloadState(fileName, progress.toDouble(), sizeInBytes)
        }
    }

    private fun updateNotification(fileName: RecentlyPlayed, progress: Int) {
        val notificationBuilder = notifications[fileName.id]

        val builder = notificationBuilder?.setProgress(100, progress, true)
            ?: NotificationCompat.Builder(this, DOWNLOAD_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_file_download_notification)
                .setContentTitle("Downloading: ${fileName.name}")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)

        notificationManager.notify(fileName.hashCode(), builder.build())
    }

}