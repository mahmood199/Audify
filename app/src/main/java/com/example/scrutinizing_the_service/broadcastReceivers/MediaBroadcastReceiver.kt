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
            MediaActionEmitter.FAST_FORWARD -> {
                fastForward(context)
            }
            MediaActionEmitter.REWIND -> {
                rewind(context)
            }
        }
    }

    private fun playMusic(context: Context?) {
        Log.d(TAG, "playMusic")
        val playIntent = Intent(MediaActionReceiver.PLAY)
        playIntent.action = MediaActionReceiver.PLAY
        context?.sendBroadcast(playIntent)
    }

    private fun pauseMusic(context: Context?) {
        Log.d(TAG, "pauseMusic")
        val pauseIntent = Intent(MediaActionReceiver.PAUSE)
        pauseIntent.action = MediaActionReceiver.PAUSE
        context?.sendBroadcast(pauseIntent)
    }

    private fun previousMusic(context: Context?) {
        Log.d(TAG, "previousMusic")
        val previousMusicIntent = Intent(MediaActionReceiver.PREVIOUS)
        previousMusicIntent.action = MediaActionReceiver.PREVIOUS
        context?.sendBroadcast(previousMusicIntent)
    }

    private fun nextMusic(context: Context?) {
        Log.d(TAG, "nextMusic")
        val nextMusicIntent = Intent(MediaActionReceiver.NEXT)
        nextMusicIntent.action = MediaActionReceiver.NEXT
        context?.sendBroadcast(nextMusicIntent)
    }

    private fun fastForward(context: Context?) {
        Log.d(TAG, "fastForward")
        val nextMusicIntent = Intent(MediaActionReceiver.FAST_FORWARD)
        nextMusicIntent.action = MediaActionReceiver.FAST_FORWARD
        context?.sendBroadcast(nextMusicIntent)
    }

    private fun rewind(context: Context?) {
        Log.d(TAG, "rewind")
        val nextMusicIntent = Intent(MediaActionReceiver.REWIND)
        nextMusicIntent.action = MediaActionReceiver.REWIND
        context?.sendBroadcast(nextMusicIntent)
    }

}