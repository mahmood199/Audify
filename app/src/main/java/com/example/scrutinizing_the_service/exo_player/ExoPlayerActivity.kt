package com.example.scrutinizing_the_service.exo_player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scrutinizing_the_service.R
import com.example.scrutinizing_the_service.exo_player.service.MusicPlayerService

class ExoPlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo_player)
    }
}