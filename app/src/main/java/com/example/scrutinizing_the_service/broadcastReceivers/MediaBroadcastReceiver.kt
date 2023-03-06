package com.example.scrutinizing_the_service.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MediaBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val TAG = "MediaBroadcastReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action) {
            MediaAction.PLAY -> {
                playMusic()
            }
            MediaAction.PAUSE -> {
                pauseMusic()
            }
            MediaAction.PREVIOUS -> {
                previousMusic()
            }
            MediaAction.NEXT -> {
                nextMusic()
            }
        }
    }

    fun playMusic() {
        Log.d(TAG, "playMusic")
    }

    private fun pauseMusic() {
        Log.d(TAG, "pauseMusic")
    }

    private fun previousMusic() {
        Log.d(TAG, "previousMusic")
    }

    private fun nextMusic() {
        Log.d(TAG, "nextMusic")
    }

}