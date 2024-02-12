package com.example.audify.v2.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
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
import androidx.media3.common.util.UnstableApi
import com.example.audify.v2.MainScreenViewModel
import com.example.audify.v2.NavigationCentral
import com.example.audify.v2.download.FileDownloaderService
import com.example.audify.v2.media3.AudioPlayerService
import com.example.audify.v2.media3.MediaPlayerAction
import com.example.audify.v2.receiver.WifiConnectionReceiver
import com.example.audify.v2.theme.AudifyTheme
import com.example.audify.v2.ui.app_icon_change.IconModel
import com.example.audify.v2.ui.catalog.MusicListViewModel
import com.example.audify.v2.util.LauncherIconManager
import com.example.data.models.Song
import dagger.hilt.android.AndroidEntryPoint


var isServiceRunning = false

@UnstableApi
@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class AudioPlayerActivity : ComponentActivity() {

    private val receiver by lazy {
        WifiConnectionReceiver()
    }

    private val iconManager by lazy {
        LauncherIconManager()
    }

    private lateinit var fileDownloadService: FileDownloaderService
    private var isBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as FileDownloaderService.LocalBinder
            fileDownloadService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    private val musicListViewModel: MusicListViewModel by viewModels()
    private val viewModel: MainScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        parseShortcutType()

        setContent {

            AudifyTheme {
                NavigationCentral(
                    playMusic = { song, index ->
                        musicListViewModel.sendMediaAction(MediaPlayerAction.PlaySongAt(index))
                        startMusicService()
                    },
                    playMusicFromRemote = {
                        startDownload(it)
                        startMusicService()
                    },
                    backPress = {
                        finish()
                    },
                    iconChangeClicked = {
                        changeAppIcon(it)
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

    private fun changeAppIcon(iconModel: IconModel) {
        iconManager.setCurrentIcon(this, iconModel.iconVariant)
    }

    private fun parseShortcutType() {
        viewModel.parseShortcutType(intent)
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, FileDownloaderService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun startDownload(recentlyPlayed: com.example.data.models.remote.saavn.Song) {
        if (isBound) {
            startDownloadService()
            fileDownloadService.startDownload(recentlyPlayed)
        }
    }

    private fun startDownloadService() {
        val intent = Intent(this, FileDownloaderService::class.java)
        startService(intent)
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
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
    AudifyTheme {
        NavigationCentral(
            playMusic = { song: Song, i: Int ->
            },
            playMusicFromRemote = {},
            backPress = {},
            iconChangeClicked = {

            },
            modifier = Modifier.background(
                MaterialTheme.colorScheme.surface
            )
        )
    }
}