package com.example.scrutinizing_the_service.v2

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import com.example.scrutinizing_the_service.BundleIdentifier
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import com.example.scrutinizing_the_service.v2.media3.AudioPlayerService
import dagger.hilt.android.AndroidEntryPoint

@UnstableApi
@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class AudioPlayerActivity : ComponentActivity() {

    private var musicPlayerService: AudioPlayerService? = null
    var isBound = false
    private lateinit var mediaController: MediaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScrutinizingTheServiceTheme {
                NavigationCentral(
                    backPress = {
                        finish()
                    },
                    playMusic = { song, index ->
                        startMusicService(song = song, index)
                    }
                )
            }
        }
    }

    private fun startMusicService(song: Song, position: Int) {
        val intent = Intent(this, AudioPlayerService::class.java).apply {
            putExtra(BundleIdentifier.BUTTON_ACTION, BundleIdentifier.ACTION_FIRST_PLAY)
            putExtra(BundleIdentifier.SONG_NAME, song.name)
            putExtra(BundleIdentifier.SONG_ARTIST, song.artist)
            putExtra(BundleIdentifier.SONG_PATH, song.path)
            putExtra(BundleIdentifier.SONG_DURATION, song.duration)
            putExtra(BundleIdentifier.SONG_POSITION, position)
        }
        startForegroundService(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}


@Preview
@Composable
fun NavigationCentralPreview() {
    ScrutinizingTheServiceTheme {
        NavigationCentral(
            backPress = {},
            playMusic = { song: Song, i: Int ->
            },
            modifier = Modifier.background(
                MaterialTheme.colorScheme.surface
            )
        )
    }
}