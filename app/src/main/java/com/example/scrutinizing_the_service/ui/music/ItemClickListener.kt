package com.example.scrutinizing_the_service.ui.music

import com.example.scrutinizing_the_service.data.Song

sealed class ItemClickListener {
    data class ItemClicked(val song: Song) : ItemClickListener()
}