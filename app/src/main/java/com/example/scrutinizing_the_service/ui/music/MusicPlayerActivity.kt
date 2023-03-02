package com.example.scrutinizing_the_service.ui.music

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.databinding.ActivityMusicPlayerBinding

class MusicPlayerActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMusicPlayerBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        SongsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setAdapter()
        Thread.sleep(1000)
        (binding.rvMusicItems.adapter as SongsAdapter).addNewItems(
            listOf(
                Song("Song 1", " Artist 1", false),
                Song("Song 2", " Artist 2", false),
                Song("Song 3", " Artist 3", false),
                Song("Song 4", " Artist 4", false),
                Song("Song 5", " Artist 5", false),
                Song("Song 6", " Artist 6", false),
            )
        )

        binding.btnAction.setOnClickListener {
            (binding.rvMusicItems.adapter as SongsAdapter).addNewItems(
                listOf(
                    Song("Song 1", " Artist 1", false),
                    Song("Song 2", " Artist 2", false),
                    Song("Song 3", " Artist 3", false),
                    Song("Song 4", " Artist 4", false),
                    Song("Song 5", " Artist 5", false),
                    Song("Song 6", " Artist 6", false),
                )
            )
        }


    }

    private fun setAdapter() {
        with(binding) {
            rvMusicItems.adapter = SongsAdapter()
        }
    }


}