package com.example.scrutinizing_the_service.services

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.scrutinizing_the_service.BundleIdentifier
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

    private lateinit var song: Song


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        getArgs(intent)

        return START_STICKY
    }

    private fun getArgs(intent: Intent?) {
        intent?.let {
            songName = it.extras?.getString(BundleIdentifier.SONG_NAME) ?: ""
            songArtist = it.extras?.getString(BundleIdentifier.SONG_ARTIST) ?: ""
            songPath = it.extras?.getString(BundleIdentifier.SONG_PATH) ?: ""
            songDuration = it.extras?.getInt(BundleIdentifier.SONG_DURATION) ?: 0
            Log.d(TAG, songName)
            Log.d(TAG, songArtist)
            Log.d(TAG, songPath)
            Log.d(TAG, songDuration.toString())
            song = Song(
                songName,
                songArtist,
                path = songPath,
                duration = songDuration
            )
            mediaPlayerNotificationBuilder.createChannel()
        }

        //mandatory to do this. Else app is crashing after 5 seconds
        startForeground(1, mediaPlayerNotificationBuilder.getNotification(song))
    }

    override fun onBind(intent: Intent?): IBinder {
        return musicBinder
    }

    inner class MusicBinder(private val musicPlayerService: MusicPlayerService) : Binder() {

        fun getService() = musicPlayerService

    }

    fun getCurrentPlayingTime(): Pair<Int, Int> {
        return Pair(mediaPlayer.currentPosition, mediaPlayer.duration)
    }

}

