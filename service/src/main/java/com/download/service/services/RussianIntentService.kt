package com.download.service.services

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import android.util.Log

class RussianIntentService : IntentService("RussianIntentService") {

    companion object {
        const val TAG = "LEARNING IntentService"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Started")
        Log.d(TAG, Thread.currentThread().name)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onHandleIntent(p0: Intent?) {
        //write logic here to be executed
    }

    override fun onDestroy() {
        Log.d(TAG, "Stopped")
        super.onDestroy()
    }

}