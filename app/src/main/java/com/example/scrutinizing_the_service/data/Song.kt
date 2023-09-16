package com.example.scrutinizing_the_service.data

import android.os.Parcelable
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val name: String,
    val artist: String,
    val album: String,
    val isFavourite: Boolean = false,
    val path: String = "",
    val duration: Int = 0
) : Parcelable


fun Song.ToMediaItem(): MediaItem {
    return MediaItem.Builder()
        .setMediaId(name)
        .setUri(path)
        .setRequestMetadata(
            MediaItem.RequestMetadata.Builder()
                .setMediaUri(path.toUri())
                .setSearchQuery(null)
                .build()
        ).build()
}

fun MediaItem.toSong(): Song {
    return Song(
        name = mediaMetadata.displayTitle.toString(),
        artist = mediaMetadata.artist.toString(),
        album = mediaMetadata.albumTitle.toString(),
        isFavourite = false,
        path = mediaMetadata.artworkUri.toString(),
        duration = 0
    )
}