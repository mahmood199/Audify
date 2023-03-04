package com.example.scrutinizing_the_service.ui.music

import android.Manifest.permission.*
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
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.databinding.ActivityMusicPlayerBinding
import com.example.scrutinizing_the_service.platform.MusicLocatorV2

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

    private fun checkPlayerState() {
        if(mediaPlayer.isPlaying)
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
        when(it) {
            is ItemClickListener.ItemClicked -> {
                setUpTheNewSong(it.song)
            }
        }
    }

    private fun setUpTheNewSong(song: Song) {
        this.song = song
        playSong(this.song)
    }

    private fun playSong(song: Song) {
        val myUri = song.path.toUri()
        mediaPlayer.reset()
        mediaPlayer.apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(applicationContext, myUri)
            prepare()
            start()
        }
    }


}