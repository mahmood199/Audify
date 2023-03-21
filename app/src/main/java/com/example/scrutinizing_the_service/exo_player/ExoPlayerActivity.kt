package com.example.scrutinizing_the_service.exo_player

import android.content.ComponentName
import android.content.Intent
import android.media.AudioManager
import android.media.browse.MediaBrowser
import android.media.session.MediaController
import android.media.session.PlaybackState
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.annotation.RequiresApi
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.databinding.ActivityExoPlayerBinding
import com.example.scrutinizing_the_service.exo_player.service.MusicPlayerService

@RequiresApi(Build.VERSION_CODES.O)
class ExoPlayerActivity : AppCompatActivity() {

    private lateinit var controllerCallbacks: MediaControllerCompat.Callback

    private val binding by lazy {
        ActivityExoPlayerBinding.inflate(layoutInflater)
    }

    private val mediaBrowser: MediaBrowserCompat by lazy {
        MediaBrowserCompat(
            this,
            ComponentName(this, MusicPlayerService::class.java),
            connectionCallbacks,
            null
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnPlayPause.setOnClickListener {
            startService(Intent(this, MusicPlayerService::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        mediaBrowser.connect()
    }

    override fun onResume() {
        super.onResume()
        volumeControlStream = AudioManager.STREAM_MUSIC
    }

    override fun onStop() {
        super.onStop()
        MediaControllerCompat.getMediaController(this).unregisterCallback(controllerCallbacks)
        mediaBrowser.disconnect()
    }


    fun buildTransportControls() {
        val mediaController = MediaControllerCompat.getMediaController(this@ExoPlayerActivity)
        // Grab the view for the play/pause button
        with(binding.btnPlayPause) {
            setOnClickListener {
                // Since this is a play/pause button, you'll need to test the current state
                // and choose the action accordingly

                val pbState = mediaController.playbackState.state
                if (pbState == PlaybackState.STATE_PLAYING) {
                    mediaController.transportControls.pause()
                } else {
                    mediaController.transportControls.play()
                }
            }
        }

        // Display the initial state
        val metadata = mediaController.metadata
        val pbState = mediaController.playbackState

        // Register a Callback to stay in sync
        mediaController.registerCallback(controllerCallback)
    }


    private val connectionCallbacks = object : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {

            val mediaController = MediaControllerCompat(
                this@ExoPlayerActivity,
                mediaBrowser.sessionToken
            )

            MediaControllerCompat.setMediaController(
                this@ExoPlayerActivity,
                mediaController
            )

            buildTransportControls()
        }

        override fun onConnectionSuspended() {
            // The Service has crashed. Disable transport controls until it automatically reconnects
        }

        override fun onConnectionFailed() {
            // The Service has refused our connection
        }
    }

    private var controllerCallback = object : MediaControllerCompat.Callback() {

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            super.onMetadataChanged(metadata)
        }

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            super.onPlaybackStateChanged(state)
        }

        override fun onSessionDestroyed() {
            super.onSessionDestroyed()
            mediaBrowser.disconnect()
        }

    }

}