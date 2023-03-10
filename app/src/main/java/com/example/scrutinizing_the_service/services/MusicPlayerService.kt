package com.example.scrutinizing_the_service.services

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.scrutinizing_the_service.BundleIdentifier
import com.example.scrutinizing_the_service.broadcastReceivers.MediaActionReceiver
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.notifs.MediaPlayerNotificationBuilder


@RequiresApi(Build.VERSION_CODES.O)
class MusicPlayerService : Service() {

    companion object {
        const val TAG = "MusicPlayerService"
    }

    private var songName: String = ""
    private var songPath: String = ""
    private var songArtist: String = ""
    private var songDuration: Int = 0

    private val musicBinder by lazy {
        MusicBinder(this)
    }

    private val mediaPlayerNotificationBuilder by lazy {
        MediaPlayerNotificationBuilder(this, mediaPlayer)
    }

    private val mediaPlayer by lazy {
        MediaPlayer()
    }

    private val runnable by lazy {
        Runnable {
            updateNotification()
        }
    }

    private val handler by lazy {
        Handler(Looper.getMainLooper())
    }

    private lateinit var song: Song

    private lateinit var customBroadcastReceiver: MusicPlayerListener

    override fun onCreate() {
        super.onCreate()
        customBroadcastReceiver = MusicPlayerListener()
        val intentFilter = IntentFilter()
        intentFilter.addAction(MediaActionReceiver.PLAY)
        intentFilter.addAction(MediaActionReceiver.PAUSE)
        intentFilter.addAction(MediaActionReceiver.PREVIOUS)
        intentFilter.addAction(MediaActionReceiver.NEXT)
        registerReceiver(customBroadcastReceiver, intentFilter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        intent?.action?.let {
            Log.d(TAG, it)
        }

        getArgs(intent)

        return START_STICKY
    }

    private fun getArgs(intent: Intent?) {
        intent?.let {
            songName = it.extras?.getString(BundleIdentifier.SONG_NAME) ?: ""
            songArtist = it.extras?.getString(BundleIdentifier.SONG_ARTIST) ?: ""
            songPath = it.extras?.getString(BundleIdentifier.SONG_PATH) ?: ""
            songDuration = it.extras?.getInt(BundleIdentifier.SONG_DURATION) ?: 0
            song = Song(
                songName,
                songArtist,
                path = songPath,
                duration = songDuration
            )
            Toast.makeText(this, songName, Toast.LENGTH_SHORT).show()
            mediaPlayerNotificationBuilder.createChannel()
        }

        //mandatory to do this. Else app is crashing after 5 seconds
        startForeground(MediaPlayerNotificationBuilder.NOTIFICATION_ID,
            mediaPlayerNotificationBuilder.getNotification(song, 0))
        playThisSong(song)
    }

    override fun onBind(intent: Intent?): IBinder {
        return musicBinder
    }

    inner class MusicBinder(private val musicPlayerService: MusicPlayerService) : Binder() {

        fun getService() = musicPlayerService

    }

    fun getCurrentPlayingTime(): Pair<Long, Long> {
        return Pair(mediaPlayer.currentPosition.toLong(), mediaPlayer.duration.toLong())
    }

    fun playThisSong(song: Song) {
        with(mediaPlayer) {
            reset()
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(song.path)
            prepare()
            start()
            updateNotification()
        }
    }


    private fun updateNotification() {
        mediaPlayerNotificationBuilder.createUpdatedNotification(song, mediaPlayer.currentPosition)
        handler.postDelayed(runnable, 2000)
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable)
        unregisterReceiver(customBroadcastReceiver)
        super.onDestroy()
    }

    fun pauseSong() {
        mediaPlayer.pause()
    }

    fun play() {
        mediaPlayer.start()
    }

    inner class MusicPlayerListener : BroadcastReceiver() {

        private val TAG = "MusicPlayerListener"

        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d(TAG, "Control Arrived here")
            intent?.action?.let {
                when (it) {
                    MediaActionReceiver.PLAY -> {
                        mediaPlayer.start()
                        Log.d(TAG, "Player Started")
                    }
                    MediaActionReceiver.PAUSE -> {
                        mediaPlayer.pause()
                        Log.d(TAG, "Player paused")
                    }
                    MediaActionReceiver.PREVIOUS -> {
                        Log.d(TAG, it)
                    }
                    MediaActionReceiver.NEXT -> {
                        Log.d(TAG, it)
                    }
                    else -> {}
                }
            }
        }

    }


}

