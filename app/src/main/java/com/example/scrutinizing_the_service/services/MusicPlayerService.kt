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
import com.example.scrutinizing_the_service.broadcastReceivers.MediaActionEmitter
import com.example.scrutinizing_the_service.broadcastReceivers.MediaActionReceiver
import com.example.scrutinizing_the_service.data.MediaPlayerStatus
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.notifs.MediaPlayerNotificationBuilder
import com.example.scrutinizing_the_service.notifs.PendingIntentHelper
import com.example.scrutinizing_the_service.platform.MusicLocatorV2


@RequiresApi(Build.VERSION_CODES.O)
class MusicPlayerService : Service() {

    companion object {
        const val TAG = "MusicPlayerService"

        const val FAST_FORWARD_TIME_IN_MS = 10000
        const val REWIND_TIME_IN_MS = 10000
    }

    private var songName: String = ""
    private var songPath: String = ""
    private var songArtist: String = ""
    private var songDuration: Int = 0
    private var album: String = ""

    private val musicBinder by lazy {
        MusicBinder()
    }

    private val mediaPlayerNotificationBuilder by lazy {
        MediaPlayerNotificationBuilder(this, mediaPlayer)
    }

    private var currentSongPosition = 0

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

    private val pendingIntentHelper by lazy {
        PendingIntentHelper(this)
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
        intentFilter.addAction(MediaActionReceiver.FAST_FORWARD)
        intentFilter.addAction(MediaActionReceiver.REWIND)
        registerReceiver(customBroadcastReceiver, intentFilter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        getArgs(intent)
        return START_STICKY
    }

    private fun getArgs(intent: Intent?) {
        intent?.let {
            val action = it.extras?.getString(BundleIdentifier.BUTTON_ACTION) ?: ""
            Log.d(TAG, action)
            when (action) {
                BundleIdentifier.ACTION_FIRST_PLAY -> {
                    processArgsToPlaySong(intent)
                }
                BundleIdentifier.ACTION_PLAY -> {
                    this.sendBroadcast(
                        pendingIntentHelper.intent.apply {
                            this.action = MediaActionEmitter.PLAY
                        }
                    )
                }
                BundleIdentifier.ACTION_PAUSE -> {
                    this.sendBroadcast(
                        pendingIntentHelper.intent.apply {
                            this.action = MediaActionEmitter.PAUSE
                        }
                    )
                }
                BundleIdentifier.ACTION_NEXT -> {
                    this.sendBroadcast(
                        pendingIntentHelper.intent.apply {
                            this.action = MediaActionEmitter.NEXT
                        }
                    )
                }
                BundleIdentifier.ACTION_PREVIOUS -> {
                    this.sendBroadcast(
                        pendingIntentHelper.intent.apply {
                            this.action = MediaActionEmitter.PREVIOUS
                        }
                    )
                }
                BundleIdentifier.ACTION_FAST_FORWARD -> {
                    this.sendBroadcast(
                        pendingIntentHelper.intent.apply {
                            this.action = MediaActionEmitter.FAST_FORWARD
                        }
                    )
                }
                BundleIdentifier.ACTION_REWIND -> {
                    this.sendBroadcast(
                        pendingIntentHelper.intent.apply {
                            this.action = MediaActionEmitter.REWIND
                        }
                    )
                }
                else -> {
                    throw Exception("Invalid media player action")
                }
            }
        }
    }

    private fun processArgsToPlaySong(it: Intent) {
        songName = it.extras?.getString(BundleIdentifier.SONG_NAME) ?: ""
        songArtist = it.extras?.getString(BundleIdentifier.SONG_ARTIST) ?: ""
        album = it.extras?.getString(BundleIdentifier.SONG_ALBUM) ?: ""
        songPath = it.extras?.getString(BundleIdentifier.SONG_PATH) ?: ""
        songDuration = it.extras?.getInt(BundleIdentifier.SONG_DURATION) ?: 0
        song = Song(
            songName,
            songArtist,
            album = album,
            path = songPath,
            duration = songDuration
        )
        currentSongPosition = it.extras?.getInt(BundleIdentifier.SONG_POSITION) ?: 0
        Toast.makeText(this, songName, Toast.LENGTH_SHORT).show()
        mediaPlayerNotificationBuilder.createChannel()


        //mandatory to do this. Else app is crashing after 5 seconds
        startForeground(
            MediaPlayerNotificationBuilder.NOTIFICATION_ID,
            mediaPlayerNotificationBuilder.getNotification(song, 0)
        )
        playThisSong(song)
    }

    override fun onBind(intent: Intent?): IBinder {
        return musicBinder
    }

    inner class MusicBinder : Binder() {

        fun getService() = this@MusicPlayerService

    }

    fun getMediaPlayerStatus(): MediaPlayerStatus {
        return MediaPlayerStatus(
            mediaPlayer.currentPosition.toLong(),
            mediaPlayer.duration.toLong(),
            song,
            mediaPlayer.isPlaying
        )
    }

    fun playThisSong(song: Song) {
        with(mediaPlayer) {
            mediaPlayerNotificationBuilder.closeAllNotification()
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
        mediaPlayerNotificationBuilder.createUpdatedNotification(
            song,
            mediaPlayer.currentPosition,
            mediaPlayer.isPlaying
        )
        handler.postDelayed(runnable, 100)
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable)
        with(mediaPlayer) {
            pause()
            stop()
            release()
        }
        mediaPlayerNotificationBuilder.closeAllNotification()
        unregisterReceiver(customBroadcastReceiver)
        super.onDestroy()
    }

    fun isPlaying() = mediaPlayer.isPlaying

    fun pauseCurrentSong() {
        mediaPlayer.pause()
    }

    fun playCurrentSong() {
        mediaPlayer.start()
    }

    inner class MusicPlayerListener() : BroadcastReceiver() {

        private val TAG = "MusicPlayerListener"

        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.action?.let {
                when (it) {
                    MediaActionReceiver.PLAY -> {
                        playCurrentSong()
                        Log.d(TAG, "Player Started")
                    }
                    MediaActionReceiver.PAUSE -> {
                        pauseCurrentSong()
                        Log.d(TAG, "Player paused")
                    }
                    MediaActionReceiver.PREVIOUS -> {
                        Log.d(TAG, it)
                        playPreviousSongSafely()
                    }
                    MediaActionReceiver.NEXT -> {
                        Log.d(TAG, it)
                        playNextSongSafely()
                    }
                    MediaActionReceiver.FAST_FORWARD -> {
                        Log.d(TAG, it)
                        fastForwardPlayer()
                    }
                    MediaActionReceiver.REWIND -> {
                        Log.d(TAG, it)
                        rewindSong()
                    }
                    else -> {}
                }
            }
        }

        private fun playPreviousSongSafely() {
            currentSongPosition--
            if (currentSongPosition < 0)
                currentSongPosition = MusicLocatorV2.getSize() - 1
            song = MusicLocatorV2.getAudioFiles()[currentSongPosition]
            playThisSong(song)
        }

        private fun playNextSongSafely() {
            currentSongPosition += 1
            if (currentSongPosition == MusicLocatorV2.getSize())
                currentSongPosition = 0
            song = MusicLocatorV2.getAudioFiles()[currentSongPosition]
            playThisSong(song)
        }

        private fun fastForwardPlayer() {
            mediaPlayer.seekTo(
                minOf(
                    mediaPlayer.currentPosition + FAST_FORWARD_TIME_IN_MS,
                    mediaPlayer.duration
                )
            )
        }

        private fun rewindSong() {
            mediaPlayer.seekTo(
                maxOf(
                    mediaPlayer.currentPosition - REWIND_TIME_IN_MS,
                    0
                )
            )
        }


    }


}

