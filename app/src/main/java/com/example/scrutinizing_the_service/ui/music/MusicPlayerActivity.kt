package com.example.scrutinizing_the_service.ui.music

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.TimeConverter
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.databinding.ActivityMusicPlayerBinding
import com.example.scrutinizing_the_service.platform.MusicLocatorV2

class MusicPlayerActivity : AppCompatActivity() {

    companion object {
        const val CODE = 1
        const val SEEK_FORWARD_TIME = 5000
        const val SEEK_BACKWARD_TIME = 5000
    }

    private val binding by lazy {
        ActivityMusicPlayerBinding.inflate(layoutInflater)
    }

    private lateinit var song: Song
    private val mediaPlayer by lazy {
        MediaPlayer()
    }

    private val runnable by lazy {
        Runnable {
            updateMusicProgressBar()
        }
    }

    private val handler by lazy {
        Handler(Looper.getMainLooper())
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

    private fun playPreviousSong() {
        songPosition--
        songPosition = (songPosition + size) % size
        with(binding) {
            playSong(
                (rvMusicItems.adapter as SongsAdapter).getItemAtPosition(songPosition)
            )
        }
    }

    private fun playNextSong() {
        songPosition++
        songPosition %= size
        with(binding) {
            playSong(
                (rvMusicItems.adapter as SongsAdapter).getItemAtPosition(songPosition)
            )
        }
    }

    private fun forwardSong() {
        currentPlayingTime =
            if (mediaPlayer.currentPosition + SEEK_FORWARD_TIME <= mediaPlayer.duration) {
                mediaPlayer.currentPosition + SEEK_FORWARD_TIME
            } else {
                mediaPlayer.duration
            }
        mediaPlayer.seekTo(currentPlayingTime)
    }

    private fun rewindSong() {
        currentPlayingTime = if (mediaPlayer.currentPosition - SEEK_BACKWARD_TIME >= 0) {
            mediaPlayer.currentPosition - SEEK_BACKWARD_TIME
        } else {
            0
        }
        mediaPlayer.seekTo(currentPlayingTime)
    }

    private fun checkPlayerState() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            currentPlayingTime = mediaPlayer.currentPosition
            with(binding) {
                btnAction.text = getString(R.string.play)
            }
        } else {
            mediaPlayer.seekTo(currentPlayingTime)
            mediaPlayer.start()
            with(binding) {
                btnAction.text = getString(R.string.pause)
                pbPlayer.progress = mediaPlayer.currentPosition
                pbPlayer.max = mediaPlayer.duration
            }
            updateMusicProgressBar()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateMusicProgressBar() {
        with(binding) {
            tvCurrentTimeStamp.text =
                TimeConverter.getConvertedTime(mediaPlayer.currentPosition.toLong())
            pbPlayer.progress = mediaPlayer.currentPosition
            pbPlayer.max = mediaPlayer.duration
        }
        handler.postDelayed(runnable, 1000)
    }

    private fun checkForPermission() {
        if (
            ContextCompat.checkSelfPermission(
                this,
                READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            (binding.rvMusicItems.adapter as SongsAdapter).addNewItems(
                MusicLocatorV2.getAllAudio(this)
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
                MusicLocatorV2.getAllAudio(this)
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
                setUpTheNewSong(it.song)
                songPosition = it.position
                size = it.totalItem
            }
        }
    }

    private fun setUpTheNewSong(song: Song) {
        this.song = song
        playSong(this.song)
    }

    @SuppressLint("SetTextI18n")
    private fun playSong(it: Song) {
        val myUri = it.path.toUri()
        mediaPlayer.reset()
        mediaPlayer.apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(this@MusicPlayerActivity, myUri)
            prepare()
            start()
            updateMusicProgressBar()
        }
        with(binding) {
            player.visibility = View.VISIBLE
            btnAction.text = getString(R.string.pause)
            tvTotalTime.text = TimeConverter.getConvertedTime(mediaPlayer.duration.toLong())
        }
    }

    override fun onDestroy() {
        //TODO while implementing service remove this
        // Because I want my music to run even though i have switched app
        // or killed this activity
        handler.removeCallbacks(runnable)
        mediaPlayer.run {
            pause()
            stop()
            reset()
            release()
        }
        super.onDestroy()
    }


}