package com.example.scrutinizing_the_service.ui.music

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.Manifest.permission.CAMERA
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.*
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.scrutinizing_the_service.BundleIdentifier
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.TimeConverter
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.databinding.ActivityMusicPlayerBinding
import com.example.scrutinizing_the_service.platform.MusicLocatorV2
import com.example.scrutinizing_the_service.services.MusicPlayerService

@RequiresApi(Build.VERSION_CODES.O)
class MusicPlayerActivity : AppCompatActivity() {

    companion object {
        const val CODE = 1
        const val SEEK_FORWARD_TIME = 5000
        const val SEEK_BACKWARD_TIME = 5000
        const val TAG = "MusicPlayerActivity"
    }

    private val binding by lazy {
        ActivityMusicPlayerBinding.inflate(layoutInflater)
    }

    private lateinit var song: Song

    private val runnable by lazy {
        Runnable {
            updateMusicProgressBar()
        }
    }

    private val handler by lazy {
        Handler(Looper.getMainLooper())
    }

    private var musicPlayerService: MusicPlayerService? = null
    var isBound = false

    private val musicPlayerServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binderBridge = service as MusicPlayerService.MusicBinder
            musicPlayerService = binderBridge.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
            musicPlayerService = null
        }

    }

    private var currentPlayingTime = 0
    private var songPosition = 0
    private var size = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setAdapter()
        checkForPermission()
        setClickListeners()
    }

    private fun setClickListeners() {
        with(binding) {
            btnAction.setOnClickListener {
                if (this@MusicPlayerActivity::song.isInitialized)
                    checkPlayerState()
            }

            btnForward.setOnClickListener {
                forwardSong()
            }

            btnRewind.setOnClickListener {
                rewindSong()
            }

            btnPrevious.setOnClickListener {
                playPreviousSong()
            }

            btnNextSong.setOnClickListener {
                playNextSong()
            }
        }
    }

    private fun startMusicService(song: Song, position: Int) {
        val intent = Intent(this, MusicPlayerService::class.java).apply {
            putExtra(BundleIdentifier.BUTTON_ACTION, BundleIdentifier.ACTION_FIRST_PLAY)
            putExtra(BundleIdentifier.SONG_NAME, song.name)
            putExtra(BundleIdentifier.SONG_ARTIST, song.artist)
            putExtra(BundleIdentifier.SONG_PATH, song.path)
            putExtra(BundleIdentifier.SONG_DURATION, song.duration)
            putExtra(BundleIdentifier.SONG_POSITION, position)
            putExtra(BundleIdentifier.SONG_ALBUM, song.album)
        }
        startForegroundService(intent)
        bindService(intent, musicPlayerServiceConnection, BIND_AUTO_CREATE)
        updateMusicProgressBar()
    }

    private fun playPreviousSong() {
        startService(Intent(this, MusicPlayerService::class.java).apply {
            putExtra(BundleIdentifier.BUTTON_ACTION, BundleIdentifier.ACTION_PREVIOUS)
        })
    }

    private fun playNextSong() {
        startService(Intent(this, MusicPlayerService::class.java).apply {
            putExtra(BundleIdentifier.BUTTON_ACTION, BundleIdentifier.ACTION_NEXT)
        })
    }

    private fun forwardSong() {
        startService(Intent(this, MusicPlayerService::class.java).apply {
            putExtra(BundleIdentifier.BUTTON_ACTION, BundleIdentifier.ACTION_FAST_FORWARD)
        })
    }

    private fun rewindSong() {
        startService(Intent(this, MusicPlayerService::class.java).apply {
            putExtra(BundleIdentifier.BUTTON_ACTION, BundleIdentifier.ACTION_REWIND)
        })
    }

    private fun checkPlayerState() {
        if (musicPlayerService?.isPlaying() == true)
            startService(Intent(this, MusicPlayerService::class.java).apply {
                putExtra(BundleIdentifier.BUTTON_ACTION, BundleIdentifier.ACTION_PAUSE)
            })
        else
            startService(Intent(this, MusicPlayerService::class.java).apply {
                putExtra(BundleIdentifier.BUTTON_ACTION, BundleIdentifier.ACTION_PLAY)
            })
    }

    @SuppressLint("SetTextI18n")
    private fun updateMusicProgressBar() {
        with(binding) {
            musicPlayerService?.let {
                val mediaPlayerData = it.getMediaPlayerStatus()
                tvCurrentTimeStamp.text = TimeConverter.getConvertedTime(mediaPlayerData.currentPlayingTime)
                tvTotalTime.text = TimeConverter.getConvertedTime(mediaPlayerData.totalDuration)
                pbPlayer.progress = ((100 * mediaPlayerData.currentPlayingTime) / mediaPlayerData.totalDuration).toInt()
                btnAction.text = getString(if (mediaPlayerData.isPlaying) R.string.pause else R.string.play)
                tvSongDisplayName.text = mediaPlayerData.song.name
/*                    Log.d(
                        TAG,
                        "FIRST" + it.getCurrentPlayingTime().first.toString()
                    )
                    Log.d(
                        TAG,
                        "SECOND" + it.getCurrentPlayingTime().second.toString()
                    )*/
            }
        }
        handler.postDelayed(runnable, 100)
    }

    private fun checkForPermission() {
        if (
            ContextCompat.checkSelfPermission(
                this,
                READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            (binding.rvMusicItems.adapter as SongsAdapter).addNewItems(
                MusicLocatorV2.fetchAllAudioFilesFromDevice(this)
            )
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)
        ) {

        } else {
            ActivityCompat.requestPermissions(
                this,
                listOf(
                    READ_EXTERNAL_STORAGE,
                    WRITE_EXTERNAL_STORAGE,
                    CAMERA,
                ).toTypedArray(), CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CODE) {
            (binding.rvMusicItems.adapter as SongsAdapter).addNewItems(
                MusicLocatorV2.fetchAllAudioFilesFromDevice(this)
            )
        } else {

        }
    }

    private fun setAdapter() {
        with(binding) {
            rvMusicItems.adapter = SongsAdapter {
                handleItemClicks(it)
            }
        }
    }

    private fun handleItemClicks(it: ItemClickListener) {
        when (it) {
            is ItemClickListener.ItemClicked -> {
                setUpTheNewSong(it.song, it.position)
                songPosition = it.position
                size = it.totalItem
            }
        }
    }

    private fun setUpTheNewSong(song: Song, position: Int) {
        this.song = song
        startMusicService(song, position)
        binding.player.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        //TODO while implementing service remove this
        // Because I want my music to run even though i have switched app
        // or killed this activity
        handler.removeCallbacks(runnable)
        unbindService(musicPlayerServiceConnection)
        super.onDestroy()
    }


}