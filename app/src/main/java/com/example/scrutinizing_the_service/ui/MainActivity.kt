package com.example.scrutinizing_the_service.ui

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.scrutinizing_the_service.databinding.ActivityMainBinding
import com.example.scrutinizing_the_service.services.BinderImpl
import com.example.scrutinizing_the_service.services.SmartServices
import com.example.scrutinizing_the_service.ui.music.MusicPlayerActivity
import com.example.scrutinizing_the_service.v2.activity.AudioPlayerActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LEARNING MainActivity"
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var smartServices = SmartServices()

    private var isServiceBound = false
    private val serviceConnection by lazy {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                val binderImpl = service as BinderImpl
                this@MainActivity.smartServices = binderImpl.getService()
                isServiceBound = true
            }

            override fun onServiceDisconnected(name: ComponentName) {
                isServiceBound = false
            }
        }
    }

    private val smartServiceIntent by lazy {
        Intent(this, SmartServices::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListeners()


    }

    private fun setClickListeners() {
        with(binding) {
            btnStartService.setOnClickListener {
                Log.d(TAG, Thread.currentThread().name)
                startService(smartServiceIntent)
            }
            btnStopService.setOnClickListener {
                stopService(smartServiceIntent)
            }
            btnBindService.setOnClickListener {
                bindService()
            }
            btnUnbindService.setOnClickListener {
                unbindService()
            }
            btnGetRandomNumber.setOnClickListener {
                setRandomNumber()
            }
            btnGoToNotifScreen.setOnClickListener {
                startActivity(
                    Intent(
                        this@MainActivity,
                        NotificationActivity::class.java
                    ).setAction("your.custom.action")
                )
            }
            btnGoToSongsScreen.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startActivity(
                        Intent(
                            this@MainActivity,
                            MusicPlayerActivity::class.java
                        ).setAction("your.custom.action")
                    )
                }
            }
            btnGoToComposeAudioScreen.setOnClickListener {
                startActivity(
                    Intent(
                        this@MainActivity,
                        AudioPlayerActivity::class.java
                    )
                        .setAction("your.custom.action")
                )
            }
        }
    }

    private fun bindService() {
        isServiceBound = true;
        bindService(smartServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun unbindService() {
        if (isServiceBound) {
            unbindService(serviceConnection)
            isServiceBound = false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setRandomNumber() {
        with(binding) {
            if (isServiceBound) {
                textView.text = "Random number: " + smartServices.getRandomNumber()
            } else {
                textView.text = "Service not bound"
            }
        }
    }
}