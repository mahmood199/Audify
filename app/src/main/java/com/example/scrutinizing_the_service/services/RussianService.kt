package com.example.scrutinizing_the_service.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class RussianService : Service() {

    companion object {
        const val TAG = "RussianService"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Started")
        Log.d(TAG, Thread.currentThread().name)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.d(TAG, "Stopped")
        super.onDestroy()
    }

}