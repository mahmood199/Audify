package com.example.scrutinizing_the_service.v2.media3

import androidx.media3.common.MediaItem

// Think of a better solution as this is very inefficient
object Controller {

    private val items = mutableListOf<MediaItem>()

    fun addItems(mediaItems: List<MediaItem>) {
        items.addAll(mediaItems)
    }

    fun getMediaItems(): List<MediaItem> {
        return items
    }
}