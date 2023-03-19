package com.example.scrutinizing_the_service.exo_player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.databinding.ActivityExoPlayerBinding
import com.example.scrutinizing_the_service.exo_player.service.MusicPlayerService

class ExoPlayerActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityExoPlayerBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}