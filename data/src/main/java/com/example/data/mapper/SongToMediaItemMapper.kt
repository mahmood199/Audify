package com.example.data.mapper

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.example.data.models.remote.saavn.Song
import javax.inject.Inject

class SongToMediaItemMapper @Inject constructor() : Mapper<Song, MediaItem> {

    override fun map(from: Song): MediaItem {
        val mediaMetaData = getMediaMetaData(from)
        return MediaItem.Builder()
            .setMediaId(from.id)
            .setUri(from.downloadUrl.last().link)
            .setMediaMetadata(mediaMetaData)
            .build()
    }

    private fun getMediaMetaData(from: Song): MediaMetadata {
        return MediaMetadata.Builder()
            .setAlbumArtist(from.album.name)
            .setDisplayTitle(from.name)
            .setSubtitle(from.releaseDate)
            .setArtworkUri(Uri.parse(from.image.first().link))
            .setReleaseYear(
                try {
                    from.year.toInt()
                } catch (e: Exception) {
                    e.printStackTrace()
                    2000
                }
            )
            .setAlbumTitle(from.album.name)
            .setTitle(from.name)
            .build()
    }

    override fun mapBack(from: MediaItem): Song {
        val album = from.mediaMetadata.albumTitle
        val duration = from.mediaMetadata.releaseYear
        val explicitContent = "No"
        val downloadUrl = listOf(from.requestMetadata.mediaUri)
        val hasLyrics: String = false.toString()
        val image = listOf(from.mediaMetadata.artworkUri)
        val id = from.mediaId
        val name = from.mediaMetadata.displayTitle
        val releaseDate = from.mediaMetadata.releaseYear
        val url = from.requestMetadata.mediaUri
        val year = from.mediaMetadata.releaseYear
        throw Exception("Implement this method")
    }
}