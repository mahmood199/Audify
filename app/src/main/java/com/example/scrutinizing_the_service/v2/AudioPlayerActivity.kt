package com.example.scrutinizing_the_service.v2

import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.downloader.PRDownloader
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.theme.ScrutinizingTheServiceTheme
import com.example.scrutinizing_the_service.v2.media3.AudioPlayerService
import com.example.scrutinizing_the_service.v2.media3.MediaPlayerAction
import com.example.scrutinizing_the_service.v2.receiver.WifiConnectionReceiver
import com.example.scrutinizing_the_service.v2.ui.catalog.MusicListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


var isServiceRunning = false

@UnstableApi
@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class AudioPlayerActivity : ComponentActivity() {

    private val receiver by lazy {
        WifiConnectionReceiver()
    }

    private val musicListViewModel: MusicListViewModel by viewModels()
    private val viewModel: MainScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        parseShortcutType()

        setContent {

            ScrutinizingTheServiceTheme {
                NavigationCentral(
                    backPress = {
                        finish()
                    },
                    playMusic = { song, index ->
                        musicListViewModel.sendMediaAction(MediaPlayerAction.PlaySongAt(index))
                        startMusicService()
                    },
                    playMusicFromRemote = {
                        startMusicService()
                    },
                    onDownloadSong = { song, index ->
                        startDownloadService(song, index)
                    },
                    viewModel = viewModel,
                )
            }
        }

        registerReceiver(
            receiver,
            IntentFilter().apply {
                addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
                addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
            }
        )
    }

    private fun parseShortcutType() {
        viewModel.parseShortcutType(intent)
    }

    private fun startDownloadService(
        song: com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Song,
        index: Int
    ) {
        Toast.makeText(this, song.url, Toast.LENGTH_SHORT).show()
        val builder = PRDownloader.download(song.url, "${song.name}$index", "${song.name}$index")
        builder.build()
            .setOnStartOrResumeListener {
                startDownloadSafely(song)
            }
            .setOnPauseListener {

            }.setOnProgressListener {

            }
    }

    private fun startDownloadSafely(song: com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Song) {
        viewModel.viewModelScope.launch(Dispatchers.IO) {

        }
    }

    private fun startMusicService() {
        if (!isServiceRunning) {
            val intent = Intent(this, AudioPlayerService::class.java)
            startForegroundService(intent)
            isServiceRunning = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
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
            playMusicFromRemote = {},
            onDownloadSong = { track: com.example.scrutinizing_the_service.v2.data.models.remote.saavn.Song, i: Int ->

            },
            modifier = Modifier.background(
                MaterialTheme.colorScheme.surface
            )
        )
    }
}