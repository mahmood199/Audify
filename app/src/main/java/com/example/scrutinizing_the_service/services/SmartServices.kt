package com.example.scrutinizing_the_service.services

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope

class SmartServices : LifecycleService() {

    companion object {
        const val TAG = "SmartServices"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // do something here
        lifecycleScope.launchWhenStarted {
            Log.d(TAG, Thread.currentThread().name)
            repeat(100000) {
                // do operation later. After checking which thread this is shitting on.
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d(TAG, "Destroyed this service")
        super.onDestroy()
    }


}