package com.example.scrutinizing_the_service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.scrutinizing_the_service.databinding.ActivityMainBinding
import com.example.scrutinizing_the_service.services.RussianService

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val russianServiceIntent by lazy {
        Intent(this, RussianService::class.java)
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
                startService(russianServiceIntent)
            }
            btnStopService.setOnClickListener {
                stopService(russianServiceIntent)
            }
        }
    }
}