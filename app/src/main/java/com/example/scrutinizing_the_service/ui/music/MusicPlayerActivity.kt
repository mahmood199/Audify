package com.example.scrutinizing_the_service.ui.music

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.databinding.ActivityMusicPlayerBinding
import com.example.scrutinizing_the_service.platform.MusicLocatorV2
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MusicPlayerActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMusicPlayerBinding.inflate(layoutInflater)
    }

    companion object {
        const val CODE = 1
    }

    private lateinit var song: Song
    private val mediaPlayer by lazy {
        MediaPlayer()
    }

    private lateinit var emitter : Flow<Int>

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("Permission: ", "Granted")
            } else {
                Log.i("Permission: ", "Denied")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setAdapter()
        checkForPermission()
        binding.btnAction.setOnClickListener {
            checkPlayerState()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updatePlayerProgress(it: Int) {
        with(binding) {
            tvCurrentTimeStamp.text = "${
                if ((it / 60) > 9) it / 60
                else "0" + it / 60
            }:${
                if ((it % 60) > 9) it % 60
                else "0" + it % 60
            }"
            pbPlayer.progress = ((it * 100.0) / song.duration).toInt()
        }
    }

    private fun checkPlayerState() {
        if (mediaPlayer.isPlaying)
            mediaPlayer.pause()
        else
            mediaPlayer.start()
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
        with(binding) {
            tvTotalTime.text = "${
                if ((it.duration / 60) > 9) it.duration / 60
                else "0" + it.duration / 60
            }:${
                if ((it.duration % 60) > 9) it.duration % 60
                else "0" + it.duration % 60
            }"
        }
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
        }

        emitter = flow {
            var current = 0
            while (current < it.duration) {
                emit(current)
                delay(1000)
                current++
            }
        }

        lifecycleScope.launch {
            emitter.collect {
                updatePlayerProgress(it)
            }
        }
    }


}