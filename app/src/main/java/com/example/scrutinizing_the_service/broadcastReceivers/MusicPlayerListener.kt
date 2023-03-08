package com.example.scrutinizing_the_service.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MusicPlayerListener : BroadcastReceiver() {

    companion object {
        private const val TAG = "MusicPlayerListener"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "Control Arrived here")
        intent?.action?.let {
            when (it) {
                MediaActionReceiver.PLAY -> {
                    Log.d(TAG, it)
                }
                MediaActionReceiver.PAUSE -> {
                    Log.d(TAG, it)
                }
                MediaActionReceiver.PREVIOUS -> {
                    Log.d(TAG, it)
                }
                MediaActionReceiver.NEXT -> {
                    Log.d(TAG, it)
                }
                else -> {}
            }
        }
    }

}