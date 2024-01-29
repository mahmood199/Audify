package com.download.service.services

import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlin.random.Random

class SmartServices : LifecycleService() {

    companion object {
        const val TAG = "LEARNING SmartServices"
    }

    @Volatile
    private var currentNumber = 0

    private val binderImpl by lazy {
        BinderImpl(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        lifecycleScope.launchWhenStarted {
            Log.d(TAG, Thread.currentThread().name)
            repeat(100000) {
                currentNumber = Random.nextInt(1, 200)
                delay(1000)
                // do operation later. After checking which thread this is shitting on.
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return binderImpl
    }


    override fun onDestroy() {
        Log.d(TAG, "Destroyed this service")
        super.onDestroy()
    }

    fun getRandomNumber() = currentNumber

}