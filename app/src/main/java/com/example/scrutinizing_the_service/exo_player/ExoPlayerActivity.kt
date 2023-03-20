package com.example.scrutinizing_the_service.exo_player

import android.content.ComponentName
import android.media.browse.MediaBrowser
import android.media.session.MediaController
import android.media.session.PlaybackState
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.media.session.MediaControllerCompat
import androidx.annotation.RequiresApi
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.databinding.ActivityExoPlayerBinding
import com.example.scrutinizing_the_service.exo_player.service.MusicPlayerService

@RequiresApi(Build.VERSION_CODES.O)
class ExoPlayerActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityExoPlayerBinding.inflate(layoutInflater)
    }

    private val mediaBrowser by lazy {
        MediaBrowser(
            this,
            ComponentName(this, MusicPlayerService::class.java),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

    private val connectionCallbacks = object : MediaBrowser.ConnectionCallback() {
        override fun onConnected() {

            // Get the token for the MediaSession
            mediaBrowser.sessionToken.also { token ->

                // Create a MediaControllerCompat
                val mediaController = MediaController(
                    this@ExoPlayerActivity,
                    token
                )

                // Save the controller
                MediaControllerCompat.setMediaController(
                    this@ExoPlayerActivity,
                    mediaController as MediaControllerCompat
                )
            }

            // Finish building the UI
            buildTransportControls()
        }

        override fun onConnectionSuspended() {
            // The Service has crashed. Disable transport controls until it automatically reconnects
        }

        override fun onConnectionFailed() {
            // The Service has refused our connection
        }
    }

    fun buildTransportControls() {
        val mediaController = MediaControllerCompat.getMediaController(this@ExoPlayerActivity)
        // Grab the view for the play/pause button
        playPause = findViewById<ImageView>(R.id.play_pause).apply {
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
        mediaController.registerCallback(contr)
    }
}