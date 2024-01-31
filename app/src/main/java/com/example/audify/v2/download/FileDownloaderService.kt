package com.example.audify.v2.download

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.data.local.datasource.FileDownloadDataSource
import com.example.data.models.remote.saavn.Song
import com.skydiver.audify.R
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

    fun startDownload(recentlyPlayed: Song) {
        launch {
            Log.d("DownloadPurpose", "startDownload")
            Log.d("DownloadPurpose", "$recentlyPlayed")
            val downloadTask = DownloadTask(
                url = recentlyPlayed.downloadUrl.first().link,
                fileName = recentlyPlayed.name
            )
            downloadTasks[recentlyPlayed.id] = downloadTask
            downloadTask.start { progress, sizeInBytes ->

                if (progress == 1.0f) {
                    removeNotification(song = recentlyPlayed, sizeInBytes)
                    downloadTasks.remove(recentlyPlayed.id)
                    cancel()
                }

                updateDb(
                    song = recentlyPlayed,
                    progress = progress,
                    sizeInBytes = sizeInBytes
                )

                updateNotification(
                    song = recentlyPlayed,
                    progress = (progress * 100).toInt()
                )
            }
        }
    }

    private suspend fun removeNotification(song: Song, sizeInBytes: Long) {
        // Instead of cancelling the notif, inform the user that download has completed
        showDownloadCompletedNotifcation()
        notificationManager.cancel(song.hashCode())
        notifications.remove(song.name)
        withContext(Dispatchers.IO) {
            downloadDataSource.updateDownloadState(song, 1.0, sizeInBytes)
        }
    }

    private fun showDownloadCompletedNotifcation() {

    }

    private suspend fun updateDb(
        song: Song,
        progress: Float,
        sizeInBytes: Long
    ) {
        withContext(Dispatchers.IO) {
            downloadDataSource.updateDownloadState(song, progress.toDouble(), sizeInBytes)
        }
    }

    private suspend fun updateNotification(song: Song, progress: Int) {
        withContext(Dispatchers.Main) {
            delay(100)
            val notificationBuilder = notifications[song.id]

            val builder = notificationBuilder?.setProgress(100, progress, false)?.setOngoing(true)
                ?: NotificationCompat.Builder(this@FileDownloaderService, DOWNLOAD_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_file_download_notification)
                    .setProgress(100, progress, false)
                    .setContentTitle("Downloading: ${song.name}")
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setOngoing(true)

            notifications[song.name] = builder

            notificationManager.notify(song.hashCode(), builder.build())
        }
    }

}