package com.example.audify.v2.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.run {
            when (intent?.action) {
                null -> {
                    Log.d("BootCompletedReceiver", "Null action received")
                }

                Intent.ACTION_BOOT_COMPLETED,
                "android.intent.action.QUICKBOOT_POWERON",
                "com.htc.intent.action.QUICKBOOT_POWERON" -> {
                    Log.d("BootCompletedReceiver", "Hello User, Device booted")
                    Log.d("BootCompletedReceiver", "Hello User, Reset the alarm that user had set")
                }

                else -> {
                    Log.d("BootCompletedReceiver", "Else block -> ${intent.action}")
                }
            }
        }
    }

}