package com.example.scrutinizing_the_service.v2.media3

import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.ForwardingPlayer
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.example.scrutinizing_the_service.BundleIdentifier
import com.example.scrutinizing_the_service.data.Song

@UnstableApi
class AudioPlayerService : MediaSessionService() {

    private lateinit var mediaSession: MediaSession
    private lateinit var player: Player
    private lateinit var notificationManager: SimpleMediaNotificationManager

    override fun onGetSession(
        controllerInfo: MediaSession.ControllerInfo
    ) = mediaSession

    override fun onCreate() {
        super.onCreate()
        player = ExoPlayer.Builder(this)
            .setSeekBackIncrementMs(10000)
            .setSeekForwardIncrementMs(10000)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                    .setUsage(C.USAGE_MEDIA)
                    .build(),
                true
            )
            .build()
            .apply {
                playbackParameters = PlaybackParameters(1.0f)
            }

        val forwardingPlayer = object : ForwardingPlayer(player) {
            override fun getAvailableCommands(): Player.Commands {
                return super.getAvailableCommands()
                    .buildUpon()
                    .remove(COMMAND_PLAY_PAUSE)
                    .remove(COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM)
                    .remove(COMMAND_SEEK_TO_PREVIOUS)
                    .remove(COMMAND_SEEK_TO_NEXT_MEDIA_ITEM)
                    .remove(COMMAND_SEEK_TO_NEXT)
                    .build()
            }
        }


        mediaSession = MediaSession.Builder(this, forwardingPlayer)
            .setCallback(CommandCallback()).apply {
                setId(packageName)
            }
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        getArgs(intent)
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getArgs(intent: Intent?) {
        intent?.let {
            val action = it.extras?.getString(BundleIdentifier.BUTTON_ACTION) ?: ""
            Log.d(TAG, action)
            when (action) {
                BundleIdentifier.ACTION_FIRST_PLAY -> {
                    processArgsToPlaySong(intent)
                }

                else -> {
                    throw Exception("Invalid media player action")
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun processArgsToPlaySong(it: Intent) {
        val songName = it.extras?.getString(BundleIdentifier.SONG_NAME) ?: ""
        val songArtist = it.extras?.getString(BundleIdentifier.SONG_ARTIST) ?: ""
        val songPath = it.extras?.getString(BundleIdentifier.SONG_PATH) ?: ""
        val songDuration = it.extras?.getInt(BundleIdentifier.SONG_DURATION) ?: 0
        val song = Song(
            name = songName,
            artist = songArtist,
            path = songPath,
            duration = songDuration
        )
        // Generate a new notification
        notificationManager = SimpleMediaNotificationManager(this, player = player, mediaSession)

        notificationManager.startNotificationService(
            this,
            mediaSession,
            song,
        )

        playThisSong(song)
    }

    private fun playThisSong(song: Song) {
        val media = MediaItem.fromUri(song.path)
        player.run {
            setMediaItem(
                media
            )
            prepare()
            play()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroy() {
        mediaSession.release()
        player.stop()
        player.release()
        if (::notificationManager.isInitialized) {
            notificationManager.cancel()
        }
        super.onDestroy()
    }

    companion object {
        const val TAG = "AudioPlayerService"
    }
}