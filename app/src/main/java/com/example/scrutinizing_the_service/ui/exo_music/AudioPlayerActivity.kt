package com.example.scrutinizing_the_service.ui.exo_music

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.scrutinizing_the_service.BundleIdentifier
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.databinding.ActivityAudioPlayerBinding
import com.example.scrutinizing_the_service.platform.MusicLocatorV2
import com.example.scrutinizing_the_service.services.MusicPlayerServiceV2
import com.example.scrutinizing_the_service.ui.music.MusicPlayerActivity
import com.google.android.exoplayer2.util.Util

class AudioPlayerActivity : AppCompatActivity() {

    companion object {
        const val CODE = 1
        const val SEEK_FORWARD_TIME = 5000
        const val SEEK_BACKWARD_TIME = 5000
        const val TAG = "AudioPlayerActivity"
    }

    private val binding by lazy {
        ActivityAudioPlayerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setAdapter()
        checkForPermission()
    }

    private fun setAdapter() {
        with(binding) {
            rvMusicItems.adapter = SongsAdapter {
                handleItemClicks(it)
            }
        }
    }

    private fun handleItemClicks(it: SongClickListener) {
        when (it) {
            is SongClickListener.ItemClicked -> {
                startMusicPlayerService(it.song, it.position)
            }
        }
    }

    private fun startMusicPlayerService(song: Song, position: Int) {
        Toast.makeText(this, "$song $position", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MusicPlayerServiceV2::class.java).apply {
            putExtra(BundleIdentifier.BUTTON_ACTION, BundleIdentifier.ACTION_FIRST_PLAY)
            putExtra(BundleIdentifier.SONG_NAME, song.name)
            putExtra(BundleIdentifier.SONG_ARTIST, song.artist)
            putExtra(BundleIdentifier.SONG_PATH, song.path)
            putExtra(BundleIdentifier.SONG_DURATION, song.duration)
            putExtra(BundleIdentifier.SONG_POSITION, position)
        }
        Util.startForegroundService(this, intent)

    }

    private fun checkForPermission() {
        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
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
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {

        } else {
            ActivityCompat.requestPermissions(
                this,
                listOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                ).toTypedArray(), MusicPlayerActivity.CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MusicPlayerActivity.CODE) {
            (binding.rvMusicItems.adapter as SongsAdapter).addNewItems(
                MusicLocatorV2.fetchAllAudioFilesFromDevice(this)
            )
        } else {

        }
    }


}