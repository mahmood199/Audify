package com.example.scrutinizing_the_service.v2.player

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Timeline

fun Player.getMediaItemById(mediaId: String): MediaItem? {
    val timeline = currentTimeline
    val windowCount = mediaItemCount
    for (windowIndex in 0 until windowCount) {
        val window = Timeline.Window()
        timeline.getWindow(windowIndex, window)

        if (window.mediaItem.mediaId == mediaId) {
            return window.mediaItem
        }
    }
    return null
}