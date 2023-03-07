package com.example.scrutinizing_the_service.services

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.*
import android.widget.Toast
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

    private val runnable by lazy {
        Runnable {
            updateNotification()
        }
    }

    private val handler by lazy {
        Handler(Looper.getMainLooper())
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
        startForeground(1, mediaPlayerNotificationBuilder.getNotification(song, 0))
        playThisSong(song)
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
        mediaPlayerNotificationBuilder.getNotification(song, mediaPlayer.currentPosition)
        handler.postDelayed(runnable, 1000)
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }




}

