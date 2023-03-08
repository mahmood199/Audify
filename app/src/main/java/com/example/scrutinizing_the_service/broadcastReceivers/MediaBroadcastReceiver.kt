package com.example.scrutinizing_the_service.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.scrutinizing_the_service.services.MusicPlayerService

@RequiresApi(Build.VERSION_CODES.O)
class MediaBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val TAG = "MediaBroadcastReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action) {
            MediaActionEmitter.PLAY -> {
                playMusic(context)
            }
            MediaActionEmitter.PAUSE -> {
                pauseMusic(context)
            }
            MediaActionEmitter.PREVIOUS -> {
                previousMusic(context)
            }
            MediaActionEmitter.NEXT -> {
                nextMusic(context)
            }
        }
    }

    private fun playMusic(context: Context?) {
        Log.d(TAG, "playMusic")
        val playIntent = Intent(context, MusicPlayerListener::class.java)
        playIntent.action = MediaActionReceiver.PLAY
        context?.sendBroadcast(playIntent)
    }

    private fun pauseMusic(context: Context?) {
        Log.d(TAG, "pauseMusic")
        val pauseIntent = Intent(context, MusicPlayerListener::class.java)
        pauseIntent.action = MediaActionReceiver.PAUSE
        context?.sendBroadcast(pauseIntent)
    }

    private fun previousMusic(context: Context?) {
        Log.d(TAG, "previousMusic")
        val previousMusicIntent = Intent(context, MusicPlayerListener::class.java)
        previousMusicIntent.action = MediaActionReceiver.PREVIOUS
        context?.sendBroadcast(previousMusicIntent)
    }

    private fun nextMusic(context: Context?) {
        Log.d(TAG, "nextMusic")
        val nextMusicIntent = Intent(context, MusicPlayerListener::class.java)
        nextMusicIntent.action = MediaActionReceiver.NEXT
        context?.sendBroadcast(nextMusicIntent)
    }

}