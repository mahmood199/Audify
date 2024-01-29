package com.download.service.ui.music

import com.example.data.models.Song


sealed class ItemClickListener {
    data class ItemClicked(val song: Song, val position: Int, val totalItem: Int) : ItemClickListener()
}