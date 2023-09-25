package com.example.scrutinizing_the_service.v2

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.common.util.UnstableApi
import com.example.scrutinizing_the_service.ServiceApplication
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import com.example.scrutinizing_the_service.v2.ui.catalog.MusicListViewModel
import com.example.scrutinizing_the_service.v2.ui.catalog.MusicListUiEvent
import com.example.scrutinizing_the_service.v2.media3.AudioPlayerService
import dagger.hilt.android.AndroidEntryPoint


var isServiceRunning = false

@UnstableApi
@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class AudioPlayerActivity : ComponentActivity() {
    private val viewModel: MusicListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val window: Window = this.window
            window.navigationBarColor = MaterialTheme.colorScheme.onSecondary.toArgb()
            ScrutinizingTheServiceTheme {
                NavigationCentral(
                    backPress = {
                        finish()
                    },
                    playMusic = { song, index ->
                        viewModel.sendUIEvent(MusicListUiEvent.PlaySongAt(index))
                        startMusicService()
                    }
                )
            }
        }
    }

    private fun startMusicService() {
        if (!isServiceRunning) {
            val intent = Intent(this, AudioPlayerService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
            isServiceRunning = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (this.applicationContext as ServiceApplication).shutDownResources()
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