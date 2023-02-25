package com.example.scrutinizing_the_service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scrutinizing_the_service.databinding.ActivityMainBinding
import com.example.scrutinizing_the_service.services.RussianService

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListeners()


    }

    private fun setClickListeners() {
        with(binding) {
            btnStartService.setOnClickListener {
                startService(Intent(this@MainActivity, RussianService::class.java))
            }
            btnStopService.setOnClickListener {
                stopService(Intent(this@MainActivity, RussianService::class.java))
            }
        }
    }
}