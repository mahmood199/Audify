package com.example.scrutinizing_the_service.ui.exo_music

import com.example.scrutinizing_the_service.data.Song

sealed class SongClickListener {
    data class ItemClicked(val song: Song, val position: Int, val totalItem: Int) : SongClickListener()
}